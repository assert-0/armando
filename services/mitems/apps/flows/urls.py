from django.urls import path

from . import views


app_name = 'flows'


urlpatterns = [
    path('', views.flows, name='flows'),
    path('<int:flow_id>', views.get_and_manage_flow, name='get_and_manage_flow')
]
