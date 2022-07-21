#!/bin/bash
export PUSH_REPO=$(echo "$REPO" | sed -e "s|.*@\(.*\)|@\1|" -e "s|/|:/|" )
export PUSH_REPO=$(echo "https://commit-bot:$COMMIT_TOKEN$PUSH_REPO")
git clone --branch $BRANCH $PUSH_REPO repo

cp repo/services/mitems/data.json .

python manage.py makemigrations
python manage.py migrate
python manage.py collectstatic --no-input

if [[ $DJANGO_SUPERUSER_PASSWORD ]]; then
echo "Creating superuser if one doesn't exist"
cat <<EOF | python manage.py shell
from django.contrib.auth import get_user_model
User = get_user_model()
User.objects.filter(username='$DJANGO_SUPERUSER_USERNAME').exists() or \
      User.objects.create_superuser('$DJANGO_SUPERUSER_USERNAME', '', '$DJANGO_SUPERUSER_PASSWORD')
EOF
fi

gunicorn config.wsgi --bind 0.0.0.0:8000
