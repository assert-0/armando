from forge.core.api import BaseAPI, Future
from forge.core.api import api_interface

from typing import List

from services import graph_maister
from .api import GraphResult


@api_interface
class Graph_MaisterAPI(BaseAPI):
    service_name = graph_maister.SERVICE_NAME

    @staticmethod
    def create_graph(self, points: List[float], title: str, xlabel: str, ylabel: str, imageFormat: str) -> Future[GraphResult]:
        """Creates graph image in base64 based on parameters"""
