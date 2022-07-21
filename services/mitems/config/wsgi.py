"""
WSGI config for mitems project.

"""
import json
import os
import sys

import forge
from django.core.wsgi import get_wsgi_application

# Have both project and service roots in path
if 'services/mitems' not in os.getcwd():
    sys.path.append(os.getcwd() + '/services/mitems')
else:
    sys.path.append(os.getcwd().replace('/services/mitems', ''))

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'config.settings.production')

forge.setup('mitems')

application = get_wsgi_application()

# Re-emit data
with open('data.json', 'r') as f:
    from base.utils import import_data_from_json
    import_data_from_json(json.load(f))
