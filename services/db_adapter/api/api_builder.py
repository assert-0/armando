from forge.core.api import BaseAPI, Future
from forge.core.api import api_interface

from services import db_adapter
from .api import User, FetchResult, UpdateResult


@api_interface
class DBAdapterAPI(BaseAPI):
    service_name = db_adapter.SERVICE_NAME

    @staticmethod
    def fetch_user(requestId: int, userId: str) -> Future[FetchResult]:
        """Returns user data based on user Id"""

    @staticmethod
    def fetch_all_users(requestId: int) -> Future[FetchResult]:
        """Returns all users data"""

    @staticmethod
    def update_user(requestId: int, newUser: User) -> Future[UpdateResult]:
        """Updates user based on user ID"""
