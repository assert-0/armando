from datetime import datetime
from enum import Enum
from typing import List, Optional

from forge.core.db import DBView
from forge.core.models import ExtendableModel

import services.db_adapter as db_adapter


class Question(ExtendableModel):
    text: str
    answers: List[str]

    def __eq__(self, other):
        return self.text == other.text

class ReasonForLackOfInterest(str, Enum):
    GENERAL_NO_INTEREST = "GENERAL_NO_INTEREST"
    NO_INTERESTING_OFFERS = "NO_INTERESTING_OFFERS"
    NO_FUNDS = "NO_FUNDS"

class User(DBView):
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
    questions: List[Question]

    @classmethod
    def get_service_name(cls) -> str:
        return db_adapter.SERVICE_NAME
