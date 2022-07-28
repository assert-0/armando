from forge.core.api import BaseAPI, Future
from forge.core.api import api_interface

from typing import List

from services import graph_maister
from .api import GraphResult


@api_interface
class Graph_MaisterAPI(BaseAPI):
    service_name = graph_maister.SERVICE_NAME

    @staticmethod
    def create_graph(self, requestId: int, x: List[float], y: List[float], graph_fmt: str, title: str, xlabel: str, ylabel: str, image_format: str) -> Future[GraphResult]:
        """Creates graph image in base64 based on parameters"""

    @staticmethod
    def create_graph_date(self, requestId: int, start_date: str, date_fmt: str, y: List[float], graph_fmt: str, title: str, xlabel: str, ylabel: str, image_format: str) -> Future[GraphResult]:
        """Create graph with date in x axis image in base64 based on parameters"""
