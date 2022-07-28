import logging

from typing import List
from io import BytesIO
import datetime as dt

import base64

from matplotlib import pyplot as plt
import matplotlib.dates as mdates
import seaborn as sb

from forge.conf import settings as forge_settings
from forge.core.api import api
from forge.core.base import BaseService

from .api import GraphResult

logger = logging.getLogger(forge_settings.DEFAULT_LOGGER)


class Graph_Maister(BaseService):

    @api
    def create_graph(self, requestId: int, x: List[float], y: List[float], graph_fmt: str, title: str, xlabel: str, ylabel: str, image_format: str) -> GraphResult:
        f = BytesIO()
        sb.set_style("darkgrid")
        #
        plt.title(title)
        plt.xlabel(xlabel)
        plt.ylabel(ylabel)
        #
        plt.plot(x, y, graph_fmt)
        #
        plt.savefig(f, format=image_format)
        #
        f.seek(0)
        #
        graphBase64 = base64.b64encode(f.read())
        #
        plt.clf()
        return GraphResult(requestId=requestId, success=True, base64Graph=graphBase64.decode('utf-8'))

    @api
    def create_graph_date(self, requestId: int, start_date: str, date_fmt: str, y: List[float], graph_fmt: str, title: str, xlabel: str, ylabel: str, image_format: str) -> GraphResult:
        f = BytesIO()
        sb.set_style("darkgrid")
        #
        plt.title(title)
        plt.xlabel(xlabel)
        plt.ylabel(ylabel)
        #
        base = dt.datetime.strptime(start_date, date_fmt).date()
        x = [base - dt.timedelta(days=x) for x in range(len(y))]
        #
        plt.gca().xaxis.set_major_formatter(mdates.DateFormatter(date_fmt))
        plt.gca().xaxis.set_major_locator(mdates.MonthLocator())
        plt.plot(x, y, graph_fmt)
        plt.gcf().autofmt_xdate()
        #
        plt.savefig(f, format=image_format)
        #
        f.seek(0)
        #
        graphBase64 = base64.b64encode(f.read())
        #
        plt.clf()
        return GraphResult(requestId=requestId, success=True, base64Graph=graphBase64.decode('utf-8'))
