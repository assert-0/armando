# Generated by Django 2.2.17 on 2020-11-26 17:29

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies: list = [
    ]

    operations = [
        migrations.CreateModel(
            name='Flow',
            fields=[
                ('flow_id', models.AutoField(db_index=True, max_length=128, primary_key=True, serialize=False)),
                ('title', models.CharField(max_length=64)),
                ('description', models.CharField(blank=True, max_length=5000)),
                ('slug', models.SlugField(blank=True, editable=False, max_length=200)),
            ],
        ),
    ]
