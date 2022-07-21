import json
import logging
from typing import List, Union, Optional, Dict

from forge.utils.store import Cache
from requests import Session

from . import settings
from .api import ComponentType, ElementType, Element, Item, Option
from .exceptions import ComponentDoesNotExist, ImportingFailed
from .utils import TimeoutHTTPAdapter

logger = logging.getLogger('mitems-api')
cache = Cache()

adapter = TimeoutHTTPAdapter()
session = Session()
session.mount("http://", adapter)
session.mount("https://", adapter)


class Mitems:
    __context = ""

    @staticmethod
    def import_data(json_data: Union[dict, list]) -> None:
        """
        Imports JSON data to Mitems.

        Eg.
            with open("data.json", "r") as f:
                data_json = json.load(f)
            Mitems.import_data(data_json)

        :param json_data: JSON data containing all the flows used in Mitems (usually sourced from `data.json`)
        """

        response = session.post(
            url=f"{settings.INTERNAL_MITEMS_URL}/import-data-api",
            auth=(settings.MITEMS_API_USERNAME, settings.MITEMS_API_PASSWORD),
            json=json_data,
        )
        if response.status_code != 200:
            error_msg = f"Response status={response.status_code} | Mitems URL: {settings.INTERNAL_MITEMS_URL}"
            raise ImportingFailed(error_msg)

    @staticmethod
    def get_element(element_slug: str) -> Element:
        """
        Gets whole element with the given slug.

        Eg. Mitems.get_element('flow-name-slug.item-name-slug.element-name-slug')

        :param element_slug: string that looks like -> 'flow-name-slug.item-name-slug.element-name-slug'.
        :return: Element object.
        """
        element_slug = Mitems.__build_unique_element_id(element_slug)
        item_slug = Mitems.__element_to_item_slug(element_slug)
        item = Mitems.get_item(item_slug)

        for element in item.elements:
            if element.slug == element_slug:
                logger.debug(f"Successfully got element from {settings.INTERNAL_MITEMS_URL} with slug: {element_slug}. "
                             f"Element: {element}")
                return element
        raise ComponentDoesNotExist(f"Element {element_slug} does not exist!")

    @classmethod
    def get_item(cls, item_slug: Optional[str] = None) -> Item:
        """
        Gets whole item with the given slug.

        Eg. Mitems.get_item('flow-name-slug.item-name-slug')

        :param item_slug: string that looks like -> 'flow-name-slug.item-name-slug'.
        :return: Item object.
        """
        item_slug = Mitems.__build_unique_item_id(item_slug)
        item_dict = Mitems.__get(item_slug, ComponentType.ITEM).copy()

        elements = []
        for element in item_dict["elements"]:
            elem = Element(**element)
            elem.content = Mitems.__build_element_content(element["content"], elem.type)
            elements.append(elem)

        item_dict.pop('elements')
        item = Item(**item_dict, elements=elements)
        logger.debug(f"Successfully got item from {settings.INTERNAL_MITEMS_URL} with slug: {item_slug}. "
                     f"Item: {item}")

        return item

    @staticmethod
    def get_text(element_slug: str) -> str:
        """
        Gets an 'text'-type element's content with the given slug.

        Eg. Mitems.get_text('flow-name-slug.item-name-slug.element-name-slug')

        :param element_slug: string that looks like -> 'flow-name-slug.item-name-slug.element-name-slug'.
        :return: String text.
        """
        element = Mitems.get_element(element_slug)
        Mitems.__check_element_type(element, ElementType.TEXT)
        assert isinstance(element.content, str)
        return element.content

    @staticmethod
    def get_options(element_slug: str) -> List[Option]:
        """
        Gets an 'options'-type element's content with the given slug.

        Eg. Mitems.get_options('flow-name-slug.item-name-slug.element-name-slug')

        :param element_slug: string that looks like -> 'flow-name-slug.item-name-slug.element-name-slug'.
        :return: List of Option objects.
        """
        element = Mitems.get_element(element_slug)
        Mitems.__check_element_type(element, ElementType.OPTIONS)
        assert isinstance(element.content, list)
        return element.content

    @staticmethod
    def get_mapping(element_slug: str) -> Dict[str, str]:
        """
        Gets a mapping {id: text} from an 'options'-type element with the given slug.

        Eg. Mitems.get_mapping('flow-name-slug.item-name-slug.element-name-slug')

        :param element_slug: string that looks like -> 'flow-name-slug.item-name-slug.element-name-slug'.
        :return: dict with form {id: text}
        """
        return {option.id: option.text for option in Mitems.get_options(element_slug)}

    @staticmethod
    def get_json(element_slug: str) -> Union[dict, list]:
        """
        Gets an 'json'-type element's content with the given slug.

        Eg. Mitems.get_json('flow-name-slug.item-name-slug.element-name-slug')

        :param element_slug: string that looks like -> 'flow-name-slug.item-name-slug.element-name-slug'.
        :return: dict or list
        """
        element = Mitems.get_element(element_slug)
        Mitems.__check_element_type(element, ElementType.JSON)
        if not isinstance(element.content, dict) and not isinstance(element.content, list):
            raise ValueError(f"Got invalid JSON: '{element.content}'")
        return element.content

    @staticmethod
    def set_context(new_context: str) -> None:
        """
        Sets new api context.

        Setting new context and calling Mitems.get_{element/text/options/json/item} methods when context is set:
        Eg. Mitems.set_context('flow-name-slug')
            -> Eg. Mitems.get_text('item-name-slug.element-name-slug')
            -> Eg. Mitems.get_item('item-name-slug')
        Eg. Mitems.set_context('flow-name-slug.item-name-slug')
            -> Eg. Mitems.get_text('element-name-slug')
            -> Eg. Mitems.get_item()

        You can overwrite context by passing full {component(element/item)}-slug as param:
            -> Eg. Mitems.get_text('flow-name-slug.item-name-slug.element-name-slug')
            -> Eg. Mitems.get_item('flow-name-slug.item-name-slug')
            Note: this won't change context, it will just ignore it for that call

        :param new_context: string that looks like -> 'flow-name-slug' or 'flow-name-slug.item-name-slug'.
        :return: None.
        """
        Mitems.__validate_context(new_context)
        Mitems.__context = f"{new_context}."

    @staticmethod
    def get_context() -> str:
        """
        Returns current context.

        Eg. Mitems.get_context()

        :return: String.
        """
        return Mitems.__context

    @staticmethod
    def reset_context() -> None:
        """
        Resets context back to empty string.

        Eg. Mitems.reset_context()

        :return: None.
        """
        Mitems.__context = ""

    @staticmethod
    def __check_element_type(element: Element, wanted_type: ElementType) -> None:
        if element.type != wanted_type:
            raise ValueError(f"Got {element.type}, but requested {wanted_type}.")

    @classmethod
    def __get(cls, slug: str, component_type: ComponentType) -> dict:
        cached_component = cache.get(slug, None)
        if cached_component:
            logger.debug(f"Successfully got cached {component_type} with slug: {slug}. "
                         f"{component_type.capitalize()}: {cached_component}")
            return cached_component

        response = session.get(
            url=f"{settings.INTERNAL_MITEMS_URL}/api/{component_type}s/{slug}",
            auth=(settings.MITEMS_API_USERNAME, settings.MITEMS_API_PASSWORD)
        )
        if response.status_code != 200:
            error_msg = f"Response status={response.status_code} | Slug: {slug} " \
                        f"| Mitems URL: {settings.INTERNAL_MITEMS_URL}"
            if response.status_code == 404:
                raise ComponentDoesNotExist(error_msg)
            raise ValueError(error_msg)

        component = json.loads(response.content)["item"]
        cache.set(slug, component, expire=settings.MITEMS_CACHE_TIMEOUT_SEC)

        return component

    @staticmethod
    def __build_element_content(content: str, element_type: str) -> Union[str, List[Option], dict, list]:
        if element_type == ElementType.TEXT:
            return content
        elif element_type == ElementType.OPTIONS:
            options = json.loads(content)
            return [Option(text=option["text"], id=option["id"]) for option in options]
        return json.loads(content)

    @staticmethod
    def __build_unique_element_id(slug: str) -> str:
        element_slug = f"{Mitems.get_context()}{slug}"
        if element_slug.count(".") == 2:
            return element_slug
        elif slug.count(".") == 2:
            return slug
        raise ValueError("Invalid slug; slug must look like this: 'flow-slug.item-slug.element-slug'. "
                         f"Your slug={slug} | context={Mitems.get_context()}")

    @staticmethod
    def __build_unique_item_id(slug: Optional[str] = None) -> str:
        if slug is None:
            slug = ""
        item_slug = f"{Mitems.get_context()}{slug}"
        if (item_slug.count(".") == 2 and item_slug[-1] == "."):
            return item_slug[:-1]
        elif item_slug.count(".") == 1:
            return item_slug
        elif slug.count(".") == 1:
            return slug
        raise ValueError("Invalid slug; slug must look like this: 'flow-slug.item-slug'. "
                         f"Your slug={slug} | context={Mitems.get_context()}")

    @staticmethod
    def __element_to_item_slug(element_slug: str) -> str:
        return '.'.join(element_slug.split('.')[:-1])

    @staticmethod
    def __validate_context(new_context: str) -> None:
        if not new_context:
            raise ValueError("'slug' can't be empty string. Use reset_context() for deleting context.")

        if not (0 <= new_context.count(".") <= 1) or '' in new_context.split("."):
            raise ValueError(
                f"Context must look like 'flow-slug' or 'flow-slug.item-slug'. "
                f"Context you are trying to set={new_context}."
            )
