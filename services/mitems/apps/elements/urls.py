from django.urls import path

from . import views


app_name = 'elements'


urlpatterns = [
    path('<str:slug>', views.get_element, name='get_element')
]
