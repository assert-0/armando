import json

from django.contrib.auth.decorators import login_required
from django.http import HttpResponseBadRequest, JsonResponse, HttpResponse
from django.shortcuts import render
from forge.messaging.consts import EmitType

from base.utils import emit_changes
from flows.models import Flow

from services.mitems.apps.base.utils import commit_changes


@login_required
def flows(request):
    if request.method == "GET":
        all_flows = Flow.objects.all().order_by('title')
        flows = [flow.get_context() for flow in all_flows]
        return JsonResponse({"flows": flows})

    elif request.method == "POST":
        body = json.loads(request.body.decode())
        if not body or not body.get("title"):
            return HttpResponseBadRequest("Bad request. Title is necessary parameter in flow creation request body.",
                                          status=400)

        new_flow = Flow()
        new_flow.update(body)
        commit_changes()
        emit_changes(new_flow, EmitType.CREATE)
        return render(request, 'index.html', {'title': 'Mitems'})


@login_required
def get_and_manage_flow(request, flow_id: str):
    if request.method == 'GET':
        flow = Flow.objects.get(pk=flow_id)
        return JsonResponse({"flow": flow.get_context_with_items()})

    elif request.method == 'PATCH':
        body = json.loads(request.body.decode())
        if not body:
            return

        if not body.get('title'):
            return HttpResponseBadRequest("Bad request. Please fill 'Title' field.", status=400)

        flow = Flow.objects.get(pk=flow_id)
        flow.update(body)
        context = flow.get_context()
        commit_changes()
        emit_changes(flow, EmitType.REPLACE)
        return JsonResponse({"flow": context})

    elif request.method == 'DELETE':
        flow = Flow.objects.get(pk=flow_id)
        response_text = f"Flow with id {flow.flow_id} and name {flow.title} deleted. It's items are deleted as well."
        flow.delete()
        commit_changes()
        emit_changes(flow, EmitType.DELETE)
        return HttpResponse(response_text, status=200)

    return HttpResponseBadRequest("Bad request. Please use one of the following methods: 'GET', 'PATCH' and 'DELETE'.",
                                  status=400)
