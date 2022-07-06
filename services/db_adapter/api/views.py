from enum import Enum
from typing import List

from forge.core.db import DBView


class ReasonForLackOfInterest(Enum):
    INTERESTED = 0
    SATISFIED_ALREADY = 1
    NO_MONEY = 2

class User(DBView):
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
    interested: bool
    reasonIfNotInterested: ReasonForLackOfInterest
    telegramChatId: str