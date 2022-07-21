import json
import os
from importlib.metadata import version as metadata_version

from django.core.management import BaseCommand
from forge.utils.templating import recursive_render
from forge_cli.utils import load_yaml, dump_yaml


class Command(BaseCommand):
    def handle(self, *_, **__) -> None:
        version = metadata_version('mitems')

        # Update config
        config_update = load_yaml(recursive_render(CONFIG_UPDATE, {'version': version}, iterations_limit=1))
        with open('config/config.yaml', 'r') as f:
            data = load_yaml(f.read())
            data['services'].update(config_update)
        dump_yaml(data, 'config/config.yaml')

        # Add data.json
        path = os.path.join(os.getcwd(), 'services/mitems')
        os.mkdir(path)
        with open('services/mitems/data.json', 'w') as f:
            json.dump([], f)


CONFIG_UPDATE = """
mitems:
  type: django
  version: {{version}}
  db:
    postgres: true
  image:
    static: true
  env:
    {% raw %}
    DJANGO_SUPERUSER_USERNAME: admin
    DJANGO_SUPERUSER_PASSWORD: '{{ env.MITEMS_ADMIN_PASSWORD }}'
    SECRET_KEY: '36ouhXQN3EmufhtQ7zle'
    {% endraw %}
  resources:
    cpu: 100m
    memory: 67Mi
"""
