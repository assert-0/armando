from forge.core.models import ExtendableModel

from enum import Enum
from typing import List


class ReasonForLackOfInterest(Enum):
    INTERESTED = 0
    SATISFIED_ALREADY = 1
    NO_MONEY = 2

class FetchResult(ExtendableModel):
    success: bool
    users: List[User]
