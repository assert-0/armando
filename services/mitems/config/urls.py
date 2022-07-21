# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.conf import settings
from django.conf.urls import include, url
from django.conf.urls.static import static
from django.contrib import admin

admin.site.site_url = '/'

urlpatterns = [
    url('jet/', include('jet.urls', 'jet')),
    url('jet/dashboard/', include('jet.dashboard.urls', 'jet-dashboard')),
    url(settings.ADMIN_URL, admin.site.urls),
    url('', include('base.urls')),
    url('api/flows/', include('flows.urls')),
    url('api/items/', include('items.urls')),
    url('api/elements/', include('elements.urls'))
]

urlpatterns += static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)
urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)

if settings.DEBUG:
    if 'debug_toolbar' in settings.INSTALLED_APPS:
        import debug_toolbar

        urlpatterns += [
            url(r'^__debug__/', include(debug_toolbar.urls)),
        ]
