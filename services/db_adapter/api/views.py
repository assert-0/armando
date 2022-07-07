from datetime import datetime
from enum import Enum
from typing import List, Optional

from forge.core.db import DBView

import services.db_adapter as db_adapter


class ReasonForLackOfInterest(str, Enum):
    INTERESTED = "INTERESTED"
    SATISFIED_ALREADY = "SATISFIED_ALREADY"
    NO_MONEY = "NO_MONEY"

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

    @classmethod
    def get_service_name(cls) -> str:
        return db_adapter.SERVICE_NAME
