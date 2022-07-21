import json

from django.core.management import BaseCommand

from base.utils import import_data_from_json


class Command(BaseCommand):

    def handle(self, *args, **kwargs) -> None:
        try:
            with open('services/mitems/data.json', 'r') as f:
                import_data_from_json(json.load(f))
        except FileNotFoundError:
            print("No 'data.json' found, skipping...")
