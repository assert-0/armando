import json
import logging

from django.conf import settings
from django.contrib.auth.decorators import login_required
from django.http import HttpResponseBadRequest, JsonResponse, HttpResponseNotFound, HttpResponse
from forge.messaging.consts import EmitType

from flows.models import Flow
from items.models import Item

from .utils import basic_authentication
from base.utils import commit_changes, emit_changes

logger = logging.getLogger(settings.LOGGER)


@login_required
def create_item(request, flow_id: str):
    data = json.loads(request.body.decode())
    if data is None or data.get('name') is None or data.get('elements') is None:
        return HttpResponseNotFound("Bad request. Name and elements parameters are necessary when creating Item object."
                                    "", status=400)

    flow = Flow.objects.get(pk=flow_id)

    item = Item.objects.create(flow=flow)
    item.update(data)
    commit_changes()
    emit_changes(flow, EmitType.REPLACE)
    return JsonResponse({"item": item.get_context()})


@login_required
def get_and_manage_item(request, item_id: str):
    if request.method == 'GET':
        item = Item.objects.get(pk=item_id)
        return JsonResponse({"item": item.get_context()})

    elif request.method == 'PATCH':
        data = json.loads(request.body.decode())
        if not (data or data.get('name') or data.get('elements')):
            return HttpResponseBadRequest("Bad request.", status=400)

        item = Item.objects.get(pk=item_id)
        item.update(data)
        commit_changes()
        emit_changes(item.flow, EmitType.REPLACE)
        return HttpResponse(f"Item with name {data['name']} and id {item_id} updated.", status=200)

    elif request.method == 'DELETE':
        item = Item.objects.get(pk=item_id)
        response_text = f"Item (id={item.item_id}, name={item.name}) deleted along with it's elements."
        item.delete()
        commit_changes()
        emit_changes(item.flow, EmitType.REPLACE)
        return HttpResponse(response_text, status=200)

    return HttpResponseBadRequest("Bad request. Please use one of the following methods: 'GET', 'PATCH' and 'DELETE'.",
                                  status=400)


@basic_authentication
def get_item(request, slug: str):
    if not slug:
        logger.error("There is no request body.")
        return HttpResponseBadRequest("Bad request.", status=400)

    item = Item.objects.get(slug=slug)
    response_data = item.get_context()
    logger.debug(f"Response data: {response_data}")

    return JsonResponse({"item": response_data})
