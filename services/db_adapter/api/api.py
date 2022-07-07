from typing import List

from forge.core.models import ExtendableModel

from .views import User


class FetchResult(ExtendableModel):
    success: bool
    users: List[User]
