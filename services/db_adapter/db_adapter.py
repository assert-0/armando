import logging
import csv
from typing import TextIO

from forge.conf import settings as forge_settings
from forge.core.api import api
from forge.core.base import BaseService


from .api import User, FetchResult
from ..db_adapter import settings
from .api
from .models import UserModel
from .views import User


logger = logging.getLogger(forge_settings.DEFAULT_LOGGER)


class DBAdapter(BaseService):
    file: TextIO

    def __init__(self):
        super().__init__()

        if UserModel.count() == 0:
            self._load_csv()

        self.start()

    @api
    def fetch_user(self, userId) -> FetchResult:
        return FetchResult(success=True, users=[UserModel.get(id=userId)])

    @api
    def fetch_all_users(self) -> FetchResult:
        return FetchResult(success=True, users=list(UserModel.all()))

    @api
    def update_user(self, new_user: User) -> bool:
        UserModel(**new_user.dict()).save()
        return True

    def _csv_dict_read(self):
        a = [{k: v for k, v in row.items()}
            for row in csv.DictReader(self.file, skipinitialspace=True)]
        return a

    def _load_csv(self):
        self.file = open(settings.DB_FILE)
        read_data = self._csv_dict_read()

        for entry in read_data:
            user = UserModel(**entry)
            user.save()

    @staticmethod
    def _process_csv_entry(entry):
        entry[8] = entry[8].split(settings.DB_ARRAY_DELIMITER)
        return entry