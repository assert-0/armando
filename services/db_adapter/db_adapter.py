import csv
import logging
from datetime import datetime
from typing import TextIO

from forge.conf import settings as forge_settings
from forge.core.api import api
from forge.core.base import BaseService
from forge.utils.datetime_helpers import str_to_date

from .api import User, FetchResult, UpdateResult
from .models import UserModel
from ..db_adapter import settings

logger = logging.getLogger(forge_settings.DEFAULT_LOGGER)


class DBAdapter(BaseService):
    file: TextIO

    def __init__(self):
        super().__init__()

        if UserModel.count({}) == 0:
            self._load_csv()

        self.start()

    @api
    def fetch_user(self, requestId: int, userId: str) -> FetchResult:
        return FetchResult(requestId=requestId, success=True, users=[UserModel.get(id=userId).to_view()])

    @api
    def fetch_all_users(self, requestId: int) -> FetchResult:
        return FetchResult(requestId=requestId, success=True, users=[user.to_view() for user in UserModel.all()])

    @api
    def update_user(self, requestId: int, newUser: User) -> bool:
        UserModel(**newUser.dict()).save()
        return UpdateResult(requestId=requestId, success=True)

    def _csv_dict_read(self):
        a = [{k: v for k, v in row.items()}
            for row in csv.DictReader(self.file, skipinitialspace=True)]
        return a

    def _load_csv(self):
        self.file = open(settings.DB_FILE)
        read_data = self._csv_dict_read()

        for entry in read_data:
            entry["datesOfPurchaseInAgency"] = [
                datetime.combine(
                    str_to_date(dateOfPurchase), datetime.min.time()
                ) for dateOfPurchase in entry["datesOfPurchaseInAgency"]
                .split(settings.DB_ARRAY_DELIMITER)
            ]
            entry["lastBoughtRE"] = datetime.combine(str_to_date(entry["lastBoughtRE"]), datetime.min.time())
            entry["lastInteractionWithAgent"] = datetime.combine(
                str_to_date(entry["lastInteractionWithAgent"]), datetime.min.time()
            )
            user = UserModel(**entry)
            user.save()
