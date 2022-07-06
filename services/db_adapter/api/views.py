from forge.core.db import DBView

from .api.api import ReasonForLackOfInterest


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
    booleanInterested: bool
    reasonIfNotInterested: ReasonForLackOfInterest