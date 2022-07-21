from environs import Env

env = Env()

MITEMS_API_USERNAME = env.str('MITEMS_API_USERNAME', 'admin')
MITEMS_API_PASSWORD = env.str('MITEMS_API_PASSWORD', 'admin')
INTERNAL_MITEMS_URL = env.str('INTERNAL_MITEMS_URL', 'http://localhost:8004')
MITEMS_CACHE_TIMEOUT_SEC = env.float('MITEMS_CACHE_TIMEOUT_SEC', 5 * 60)
