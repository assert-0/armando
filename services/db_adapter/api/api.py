from forge.core.models import ExtendableModel

from typing import List

class User(ExtendableModel):
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

class FetchResult(ExtendableModel):
    success: bool
    users: List[User]
