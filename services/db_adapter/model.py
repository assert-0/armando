from forge.core.db import DBModel

from .api.views import User
from .api.api import ReasonForLackOfInterest


class UserModel(DBModel):
    id: str
    name: str
    surname: str
    gender: str
    age: int
    email: str
    phoneNumber: str
    amountOfBoughtRE: int
    datesOfPurchaseInAgency: List[str]
    lastBoughtRE: str
    lastInteractionWithAgent: str
    booleanInterested: bool
    reasonIfNotInterested: ReasonForLackOfInterest


    @classmethod
    def get_view(cls: Type[ModelT]) -> Optional[Type[DBView]]:
        return User
