# -*- coding: utf-8 -*-
"""
Local settings

- Run in Debug mode
- Use console backend for emails
- Add Django Debug Toolbar
- Add django-extensions as app
"""

from .common import *  # noqa: F403

DEBUG = True
TEMPLATES[0]['OPTIONS']['debug'] = True  # type: ignore # noqa: F405

SECRET_KEY = env('SECRET_KEY', default='=(v*oxs8qdzgers5x@b1+qp%s8=z!7)bj1_8&dz^)oaf+6tw2)')  # noqa: F405
ALLOWED_HOSTS = ['*']
SITE_URL = env.str('SITE_URL', 'http://localhost:8004')  # noqa: F405
INTERNAL_SITE_URL = env.str('INTERNAL_SITE_URL', 'http://localhost:8004')  # noqa: F405

EMAIL_BACKEND = 'django.core.mail.backends.console.EmailBackend'
EMAIL_PORT = 25
EMAIL_HOST = 'localhost'
EMAIL_USE_TLS = False

MODULE = 'local'

CACHES = {
    'default': {
        'BACKEND': 'django.core.cache.backends.dummy.DummyCache',
        'LOCATION': '127.0.0.1:11211',
    }
}

MIDDLEWARE += ['debug_toolbar.middleware.DebugToolbarMiddleware']  # noqa: F405
INSTALLED_APPS += ('debug_toolbar',)  # type: ignore # noqa: F405

INTERNAL_IPS = ['127.0.0.1']
