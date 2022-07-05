from environs import Env

env = Env()

DB_FILE = env.str('DB_FILE')
DB_DELIMITER = env.str('DB_DELIMITER', ',')
DB_ARRAY_DELIMITER = env.str('DB_ARRAY_DELIMITER', ';')
