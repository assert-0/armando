import base64
import json
import logging
import os
import subprocess
from functools import wraps
from operator import itemgetter
from typing import List

from django.conf import settings
from django.http import HttpResponse
from forge.messaging.consts import EmitType
from forge.module import Module

from elements.models import Element
from flows.models import Flow
from items.models import Item
from services.mitems.api import Flow as FlowView
from services.mitems.api.settings import MITEMS_API_USERNAME, MITEMS_API_PASSWORD

logger = logging.getLogger(settings.LOGGER)


def get_data() -> List[dict]:
    data = sorted([{
        "title": flow.title,
        "description": flow.description,
        "items": sorted([{
            "name": item.name,
            "elements": sorted([{
                "description": element.description,
                "type": element.type,
                "content": element.content,
            } for element in item.element_set.all()], key=itemgetter("description"))
        } for item in flow.item_set.all()], key=itemgetter("name"))
    } for flow in Flow.objects.all()], key=itemgetter("title"))
    return data


def import_data_from_json(json_data: List[dict]) -> None:
    Flow.objects.exclude(title__in=[f['title'] for f in json_data]).delete()

    for flow_data in json_data:
        flow = Flow.objects.update_or_create(title=flow_data['title'],
                                             defaults={"title": flow_data['title'],
                                                       "description": flow_data['description']})[0]

        Item.objects.filter(flow=flow).exclude(name__in=[i['name'] for i in flow_data['items']]).delete()

        for item_data in flow_data["items"]:
            item = Item.objects.update_or_create(name=item_data['name'], flow=flow,
                                                 defaults={"name": item_data['name'], "flow": flow})[0]

            Element.objects.filter(item=item).exclude(
                description__in=[e['description'] for e in item_data['elements']]).delete()

            element_index = 0
            for element_data in item_data["elements"]:
                Element.objects.update_or_create(description=element_data['description'], item=item,
                                                 defaults={"description": element_data['description'],
                                                           "item": item,
                                                           "type": element_data["type"],
                                                           "content": element_data['content'],
                                                           "index": element_index})
                element_index += 1

        emit_changes(flow, EmitType.REPLACE)


def basic_authentication(function):
    @wraps(function)
    def authentication_decorator(request):
        if base_http_auth(request, MITEMS_API_USERNAME, MITEMS_API_PASSWORD):
            return function(request)
        else:
            return HttpResponse("Invalid login credentials.", status=401)

    return authentication_decorator


def base_http_auth(request, username: str, password: str) -> bool:
    logger.debug("Trying to pass basic HTTP auth...")

    auth_header = request.META.get('HTTP_AUTHORIZATION', "")
    token_type, _, credentials = auth_header.partition(' ')

    logger.debug("Got request auth headers")

    credentials_string = f'{username}:{password}'
    expected = base64.b64encode(bytes(credentials_string, encoding='utf-8')).decode()

    result = token_type == 'Basic' and credentials == expected

    if result:
        logger.debug("Basic HTTP auth passed")
    else:
        logger.debug("Basic HTTP auth NOT passed")

    return result


def commit_changes():
    data = get_data()
    if Module.is_local():
        if "mitems" in os.getcwd():
            dump_path = './data.json'
        else:
            dump_path = './services/mitems/data.json'
    else:
        dump_path = 'repo/services/mitems/data.json'

    with open(dump_path, 'w') as f:
        json.dump(data, f, ensure_ascii=False, indent=2)

    if not Module.is_local():
        subprocess.Popen(["bash", "commit_changes.sh"])


def emit_changes(flow: Flow, emit_type: EmitType):
    FlowView(**flow.get_context_with_items()).emit(emit_type)
