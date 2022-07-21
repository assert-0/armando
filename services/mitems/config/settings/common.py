# -*- coding: utf-8 -*-
"""
Django settings for mitems project.

For more information on this file, see
https://docs.djangoproject.com/en/dev/topics/settings/

For the full list of settings and their values, see
https://docs.djangoproject.com/en/dev/ref/settings/
"""
from __future__ import absolute_import, unicode_literals

import logging
import sys
from typing import List

import environ
from environs import Env

from forge.conf import settings as forge_settings
from forge.utils.postgres import get_database_name

ROOT_DIR = environ.Path(__file__) - 3

env = Env()

APPS_DIR = ROOT_DIR.path('apps')
sys.path.append(APPS_DIR.root)

ALLOWED_HOSTS: List[str] = []

DJANGO_APPS = (
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'jet.dashboard',
    'jet',
    'django.contrib.admin',
)

PRIMARY_THIRD_PARTY_APPS = ()

THIRD_PARTY_APPS = (
    'django_json_widget',
)

LOCAL_APPS = (
    'base',
    'flows',
    'items',
    'elements',
)

INSTALLED_APPS = DJANGO_APPS + THIRD_PARTY_APPS + LOCAL_APPS

MIDDLEWARE = [
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.locale.LocaleMiddleware',
    'django.middleware.common.CommonMiddleware',
]

FIXTURE_DIRS = (
    str(APPS_DIR.path('fixtures')),
)

ADMINS = (
    ("""Mindsmiths""", 'hello@mindsmiths.com'),
)

MANAGERS = ADMINS

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.postgresql_psycopg2',
        'NAME': get_database_name(),
        'USER': forge_settings.POSTGRES_CLIENT_USERNAME,
        'PASSWORD': forge_settings.POSTGRES_CLIENT_PASSWORD,
        'HOST': forge_settings.POSTGRES_CLIENT_HOST,
        'PORT': forge_settings.POSTGRES_CLIENT_PORT
    }
}
DATABASES['default']['ATOMIC_REQUESTS'] = True  # type: ignore

TIME_ZONE = 'UTC'
LANGUAGE_CODE = 'en-us'
SITE_ID = 1
USE_I18N = True
USE_L10N = True
USE_TZ = True

DEFAULT_AUTO_FIELD = 'django.db.models.AutoField'

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [
            str(APPS_DIR.path('templates')),
        ],
        'OPTIONS': {
            'loaders': [
                'django.template.loaders.filesystem.Loader',
                'django.template.loaders.app_directories.Loader',
            ],
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.template.context_processors.i18n',
                'django.template.context_processors.media',
                'django.template.context_processors.static',
                'django.template.context_processors.tz',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]

STATIC_ROOT = '/var/www/assets/static'
STATIC_URL = '/static/'

STATICFILES_DIRS = [str(APPS_DIR.path('static'))]

STATICFILES_FINDERS = (
    'django.contrib.staticfiles.finders.FileSystemFinder',
    'django.contrib.staticfiles.finders.AppDirectoriesFinder',
)

MEDIA_ROOT = '/var/www/assets/media'
MEDIA_URL = '/media/'

ROOT_URLCONF = 'config.urls'
WSGI_APPLICATION = 'config.wsgi.application'

AUTH_PASSWORD_VALIDATORS = [
    {
        'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
    },
]

AUTHENTICATION_BACKENDS = (
    'django.contrib.auth.backends.ModelBackend',
)

ADMIN_URL = r'^admin/'

SESSION_COOKIE_NAME = 'mitems'

# Your common stuff: Below this line define 3rd party library settings
# ------------------------------------------------------------------------------

LOGGER = 'forge.mitems'
LOGIN_URL = '/login'

LOGGING = forge_settings.LOGGING

JET_SIDE_MENU_COMPACT = True

TINYMCE_DEFAULT_CONFIG = {
    'plugins': "table,spellchecker,paste,searchreplace",
    'theme': "advanced",
    'cleanup_on_startup': True,
    'custom_undo_redo_levels': 10,
    'width': '100%',
    'height': 300,
    'content_style': '.mcecontentbody{font-size:15px;}',
    'entity_encoding': 'raw',
    'force_br_newlines': False,
    'force_p_newlines': False,
    'forced_root_block': ''
}

try:
    import sentry_sdk
    from sentry_sdk.integrations.django import DjangoIntegration
    from sentry_sdk.integrations.logging import LoggingIntegration

    if forge_settings.SENTRY_CONFIG['dsn']:
        sentry_logging = LoggingIntegration(
            level=logging.INFO,
            event_level=logging.ERROR
        )

        sentry_sdk.init(
            integrations=[sentry_logging, DjangoIntegration()],
            **forge_settings.SENTRY_CONFIG
        )
except ModuleNotFoundError:
    pass
