import logging
import csv
from typing import TextIO

from forge.conf import settings as forge_settings
from forge.core.api import api
from forge.core.base import BaseService


from .api import User, FetchResult
from ..db_adapter import settings
from .api
from .model import UserModel
from .views import User


logger = logging.getLogger(forge_settings.DEFAULT_LOGGER)


class DBAdapter(BaseService):
    file: TextIO

    def __init__(self):
        super().__init__()

        self.file = open(settings.DB_FILE)
        read_data = self._csv_dict_read()

        for entry in read_data:
            user = UserModel(id=entry["id"], name=entry["name"], surname=entry["surname"], gender=entry["gender"], age=entry["age"],
            email=entry["email"], phoneNumber=entry["phoneNumber"], amountOfBoughtRE=entry["amountOfBoughtRE"], 
            datesOfPurchaseInAgency=entry["datesOfPurchaseInAgency"], lastBoughtRE=entry["lastBoughtRE"],
            lastInteractionWithAgent=entry["lastInteractionWithAgent"], booleanInterested=entry["booleanInterested"], 
            reasonIfNotInterested=entry["reasonIfNotInterested"])
            user.save()

        #user = UserModel(id=slkdjf, bla2=bla4)
        #user.save()

        self.start()
    
    def _csv_dict_read(self):
        a = [{k: v for k, v in row.items()}
            for row in csv.DictReader(self.file, skipinitialspace=True)]
        return a
    

    def _valid(self):
        return self.file.readable()

    def _get_csv(self):
        return csv.reader(self.file, delimiter=settings.DB_DELIMITER)

    @staticmethod
    def _process_csv_entry(entry):
        entry[8] = entry[8].split(settings.DB_ARRAY_DELIMITER)
        return entry

    @api
    def fetch_user(self, phone: str) -> FetchResult:
        
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
    def fetch_all_users(self) -> FetchResult:
        if (not self._valid()):
            return FetchResult(success=False, users=[])

        csv_file = self._get_csv()

        return FetchResult(success=True, users=[User(*self._process_entry(entry)) for entry in csv_file])
    
    @api
    def fetch_user(self, params = dict) -> FetchResult:
        

    @api
    def fetch_all_users(self) -> FetchResult:
        return FetchResult(success=True, users=self.all()) #??
        
    @api
    def update_user(self, new_user: User):
        old_user = self.get(new_user["id"])
        old_user.update(new_user)
        return






