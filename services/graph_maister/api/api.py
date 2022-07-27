from forge.core.models import ExtendableModel


class GraphResult(ExtendableModel):
    requestId: int
    success: bool
    base64Graph: str
