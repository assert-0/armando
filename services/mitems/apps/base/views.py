import json
import logging
import threading

from django.contrib.auth import authenticate, login as django_login
from django.contrib.auth.decorators import login_required
from django.http import JsonResponse, HttpResponse
from django.shortcuts import render, redirect
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_http_methods, require_POST
from forge.core.consts import LOGGER

from base.utils import get_data, import_data_from_json, basic_authentication

from services.mitems.apps.base.utils import commit_changes

logger = logging.getLogger(f'{LOGGER}.mitems')
FILE = "file"


@login_required
def index(request):
    return render(request, 'index.html', {'title': 'Mitems'})


def login(request):
    if request.method == "POST":
        username = request.POST['username']
        password = request.POST['password']
        user = authenticate(request, username=username, password=password)
        if user is not None:
            django_login(request, user)
            return redirect("/")
            # Redirect to a success page.

        else:
            # Return an 'invalid login' error message.
            return render(request, 'login.html', {'title': 'Login Mitems'})

    elif request.method == "GET":
        return render(request, 'login.html', {'title': 'Login Mitems'})


@login_required(login_url='/admin')
@require_http_methods(['GET', 'POST'])
def import_data(request):
    try:
        if FILE in request.FILES:
            json_text = request.FILES[FILE].read()
        else:
            body = request.body.decode()
            json_text = json.loads(body)['text']
        import_data_from_json(json.loads(json_text))
    except Exception as e:
        return JsonResponse({'success': False, 'errorMessage': repr(e)})
    commit_changes()
    return JsonResponse({'success': True})


@basic_authentication
@require_POST
@csrf_exempt
def import_data_api(request):
    threading.Thread(target=import_data_from_json, args=(json.loads(request.body.decode()),)).start()
    commit_changes()
    return JsonResponse({'success': True})


@login_required
def export_data(request):
    data = get_data()
    response = HttpResponse(json.dumps(data, ensure_ascii=False, indent=2), content_type='application/json')
    response['Content-Disposition'] = 'attachment; filename=data.json'
    return response
