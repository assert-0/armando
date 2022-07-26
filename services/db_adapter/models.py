from datetime import datetime
from typing import List, Type, TypeVar, Optional

from forge.core.db import DBModel, DBView

from .api import ReasonForLackOfInterest, User, Question, Activity

ModelT = TypeVar('ModelT', bound='DBModel')


class UserModel(DBModel):
    id: str
    name: str
    surname: str
    gender: str
    age: int
    email: str
    phoneNumber: str
    amountOfBoughtRE: int
    datesOfPurchaseInAgency: List[datetime]
    lastBoughtRE: datetime
    lastInteractionWithAgent: datetime
    interested: Optional[bool]
    reasonIfNotInterested: Optional[ReasonForLackOfInterest]
    telegramChatId: Optional[str]
    boughtRE: Optional[bool]
    questions: List[Question] = []
    activities: List[Activity] = []

    @classmethod
    def get_view(cls: Type[ModelT]) -> Optional[Type[DBView]]:
        return User
