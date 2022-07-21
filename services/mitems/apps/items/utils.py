import base64
import logging
from functools import wraps

from django.conf import settings
from django.http import HttpResponse

from services.mitems.api.settings import MITEMS_API_PASSWORD, MITEMS_API_USERNAME

logger = logging.getLogger(settings.LOGGER)


def basic_authentication(function):
    @wraps(function)
    def authentication_decorator(request, slug):
        if base_http_auth(request, MITEMS_API_USERNAME, MITEMS_API_PASSWORD):
            return function(request, slug)
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
