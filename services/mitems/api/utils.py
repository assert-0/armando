from requests.adapters import HTTPAdapter
from urllib3 import Retry

DEFAULT_TIMEOUT = 2

DEFAULT_RETRY_STRATEGY = Retry(total=3, status_forcelist=[400, 401, 402, 403, 429, 500, 502, 503],
                               method_whitelist=["GET"], backoff_factor=1)


class TimeoutHTTPAdapter(HTTPAdapter):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

        self.timeout = DEFAULT_TIMEOUT
        if "timeout" in kwargs:
            self.timeout = kwargs["timeout"]
            del kwargs["timeout"]

        self.max_retries = DEFAULT_RETRY_STRATEGY
        if "max_retries" in kwargs:
            self.max_retries = kwargs["max_retries"]
            del kwargs["max_retries"]

    def send(self, request, **kwargs):
        timeout = kwargs.get("timeout")
        if timeout is None:
            kwargs["timeout"] = self.timeout

        return super().send(request, **kwargs)
