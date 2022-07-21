from django.urls import path

from . import views


app_name = 'base'


urlpatterns = [
    path('', views.index, name='index'),
    path('login', views.login, name='login'),
    path('import-data', views.import_data, name='import_data'),
    path('export-data', views.export_data, name='export_data'),
    path('import-data-api', views.import_data_api, name='import_data_api'),
]
