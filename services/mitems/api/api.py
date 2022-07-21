from enum import Enum
from typing import Union, Dict, List, Optional

import pymongo
from forge.core.api import ExtendableModel
from forge.core.db import DBView
from pymongo import IndexModel


class ComponentType(str, Enum):
    ITEM = "item"
    ELEMENT = "element"


class Option(ExtendableModel):
    id: str
    text: str


class ElementType(str, Enum):
    OPTIONS = "OPTIONS"
    TEXT = "TEXT"
    JSON = "JSON"


class Element(ExtendableModel):
    id: str
    description: str
    slug: str
    type: ElementType
    content: Union[str, list, Dict[str, Option], dict]
    index: int
    itemId: str


class Item(ExtendableModel):
    id: str
    name: str
    slug: str
    flowId: str
    elements: List[Element]


class Flow(DBView):
    id: str
    title: str
    description: str
    slug: str
    items: List[Item]

    @classmethod
    def get_service_name(cls) -> str:
        return 'mitems'

    @classmethod
    def get_collection_indexes(cls) -> Optional[List[IndexModel]]:
        return super().get_collection_indexes() + [
            IndexModel([('slug', pymongo.HASHED)]),
            IndexModel([('items.slug', pymongo.HASHED)]),
            IndexModel([('items.elements.slug', pymongo.HASHED)]),
        ]
