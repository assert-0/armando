from forge.core.api import BaseAPI, Future
from forge.core.api import api_interface

from services import db_adapter
from .api import FetchResult


@api_interface
class Db_AdapterAPI(BaseAPI):
    service_name = db_adapter.SERVICE_NAME

    @staticmethod
    def fetch_user(phone: str) -> Future[FetchResult]:
        """Returns user data based on specified phone"""

    @staticmethod
    def fetch_users() -> Future[FetchResult]:
        """Returns all users data"""
