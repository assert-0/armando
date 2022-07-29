import logging

from typing import List
from io import BytesIO
import datetime as dt

import base64

from matplotlib import pyplot as plt
import matplotlib.dates as mdates
import seaborn as sb
import pandas as pd

from forge.conf import settings as forge_settings
from forge.core.api import api
from forge.core.base import BaseService

from .api import GraphResult

logger = logging.getLogger(forge_settings.DEFAULT_LOGGER)


class Graph_Maister(BaseService):

    @api
    def create_graph(self, requestId: int, x: List[float], y: List[float], graphFmt: str, title: str, xlabel: str, ylabel: str, imageFormat: str) -> GraphResult:
        f = BytesIO()
        sb.set_style("darkgrid")
        sb.set(rc = {'figure.figsize':(15,12)})
        sb.set(font_scale = 3)
        #
        plt.xlabel(xlabel)
        plt.ylabel(ylabel)
        #
        plt.plot(x, y, graphFmt, linewidth=2)
        plt.margins(0)
        #
        plt.savefig(f, format=imageFormat)
        #
        f.seek(0)
        #
        graphBase64 = base64.b64encode(f.read())
        #
        plt.clf()
        return GraphResult(requestId=requestId, success=True, base64Graph=graphBase64.decode('utf-8'))

    @api
    def create_graph_date(self, requestId: int, startDate: str, dateFmt: str, y: List[float], graphFmt: str, title: str, xlabel: str, ylabel: str, imageFormat: str) -> GraphResult:
        f = BytesIO()
        sb.set_style("darkgrid")
        sb.set(rc = {'figure.figsize':(15,12)})
        sb.set(font_scale = 3)
        #
        plt.title(title.capitalize())
        plt.xlabel(xlabel)
        plt.ylabel(ylabel)
        #
        base = dt.datetime.strptime(startDate, dateFmt).date()
        x = [base + dt.timedelta(weeks=x) for x in range(len(y))]
        #
        plt.gca().xaxis.set_major_formatter(mdates.DateFormatter(dateFmt))
        plt.gca().xaxis.set_major_locator(mdates.MonthLocator())
        plt.plot(x, y, graphFmt, linewidth=2)
        plt.gcf().autofmt_xdate()
        #
        plt.savefig(f, format=imageFormat)
        #
        f.seek(0)
        #
        graphBase64 = base64.b64encode(f.read())
        #
        plt.clf()
        return GraphResult(requestId=requestId, success=True, base64Graph=graphBase64.decode('utf-8'))
