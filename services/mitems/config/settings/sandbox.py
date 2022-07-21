from .common import *  # noqa: F403, F811

REPO_SITE_SLUG = 'mitems_sandbox'

DEBUG = True
TEMPLATES[0]['OPTIONS']['debug'] = True  # type: ignore # noqa: F405
SECRET_KEY = env('SECRET_KEY', default='KE00K%SJ8vjYX46!%6$W3Js1OewOAmk@')  # noqa: F405

SITE_URL = env('SITE_URL')  # noqa: F405
INTERNAL_SITE_URL = env('INTERNAL_SITE_URL')  # noqa: F405

ALLOWED_HOSTS += ['localhost', SITE_URL.split('//')[-1], INTERNAL_SITE_URL.split('//')[-1]]  # noqa: F405

MODULE = 'sandbox'
