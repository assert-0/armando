from typing import List

from forge.core.models import ExtendableModel

from .views import User


class FetchResult(ExtendableModel):
    requestId: int
    success: bool
    users: List[User]

class UpdateResult(ExtendableModel):
    requestId: int
    success: bool
