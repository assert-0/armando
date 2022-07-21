from django.db import models
from slugify import slugify


class Flow(models.Model):
    flow_id = models.AutoField(primary_key=True, db_index=True)
    title = models.CharField(max_length=64)
    description = models.CharField(max_length=5000, blank=True)
    slug = models.SlugField(editable=False, max_length=200, blank=True)

    def save(self, *args, **kwargs):
        slug = slugify(self.title)
        self.slug = slug
        for item in self.item_set.all():
            item.save()
        super(Flow, self).save(*args, **kwargs)

    def update(self, body: dict) -> None:
        self.title = body["title"]
        self.description = body["description"]
        self.save()

    def get_context(self) -> dict:
        return {
            'id': self.flow_id,
            'title': self.title,
            'description': self.description,
            'slug': self.slug,
        }

    def get_context_with_items(self) -> dict:
        context = self.get_context()
        context["items"] = [item.get_context() for item in self.item_set.all().order_by('name')]
        return context

    def __str__(self):
        return self.slug
