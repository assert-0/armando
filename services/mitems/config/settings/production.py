# -*- coding: utf-8 -*-
"""
Production Configurations


- Use sentry for error logging


"""
from __future__ import absolute_import, unicode_literals

from .common import *  # noqa: F403, F811

REPO_SITE_SLUG = 'mitems_production'

INSTALLED_APPS += ()  # noqa: F405

DEBUG = False
SECRET_KEY = env('SECRET_KEY')  # noqa: F405

SITE_URL = env('SITE_URL')  # noqa: F405
INTERNAL_SITE_URL = env('INTERNAL_SITE_URL')  # noqa: F405

ALLOWED_HOSTS += ['localhost', SITE_URL.split('//')[-1], INTERNAL_SITE_URL.split('//')[-1]]  # noqa: F405

MODULE = 'production'
