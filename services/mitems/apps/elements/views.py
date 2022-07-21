import logging

from django.http import HttpResponseBadRequest, JsonResponse
from django.conf import settings

from elements.models import Element
from elements.utils import basic_authentication


logger = logging.getLogger(settings.LOGGER)


@basic_authentication
def get_element(request, slug: str):
    if not slug:
        logger.error("There is no request body.")
        return HttpResponseBadRequest("Bad request.", status=400)

    element = Element.objects.get(slug=slug)
    response_data = element.to_dict()

    logger.debug(f"Response data: {response_data}")
    return JsonResponse({"item": response_data})
