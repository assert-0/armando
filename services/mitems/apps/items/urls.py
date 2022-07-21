from django.urls import path

from . import views


app_name = 'items'


urlpatterns = [
    path('<int:flow_id>/create', views.create_item, name='create_item'),
    path('<int:item_id>', views.get_and_manage_item, name='get_and_manage_item'),
    path('<str:slug>', views.get_item, name='get_item')
]
