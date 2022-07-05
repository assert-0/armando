import logging

from forge.conf import settings as forge_settings
from forge.core.api import api
from forge.core.base import BaseService

import csv
from typing import TextIO
from .api import User, FetchResult

from ..db_adapter import settings

logger = logging.getLogger(forge_settings.DEFAULT_LOGGER)


class Db_Adapter(BaseService):
    file: TextIO
    def start(self):
        self.file = open(settings.DB_FILE)

    def _valid(self):
        return self.file.readable()

    def _get_csv(self):
        return csv.reader(self.file, delimiter=settings.DB_DELIMITER)

    @staticmethod
    def _process_entry(entry):
        entry[8] = entry[8].split(settings.DB_ARRAY_DELIMITER)
        return entry

    @api
    def fetch_user(self, phone: str) -> FetchResult:
        # This function is automatically called when another service invokes it through the service's API.

        if (not self._valid()):
            return FetchResult(success=False, users=[])

        csv_file = self._get_csv()
        
        for entry in csv_file:
            if (entry[6] == phone):
                return FetchResult(success=True, users=[User(*self._process_entry(entry))])

        # Optionally return something which will be sent as a callback signal to the calling service
        # It can be anything that is serializable to JSON (primitive, dict, list, dataclass...)
        return FetchResult(success=False, users=[])

    @api
    def fetch_users(self) -> FetchResult:
        if (not self._valid()):
            return FetchResult(success=False, users=[])

        csv_file = self._get_csv()

        return FetchResult(success=True, users=[User(*self._process_entry(entry)) for entry in csv_file])
