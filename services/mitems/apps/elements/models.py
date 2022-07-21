from django.db import models
from slugify import slugify


class Element(models.Model):
    element_id = models.AutoField(primary_key=True, db_index=True)
    description = models.CharField(max_length=64)
    type = models.CharField(max_length=64)
    content = models.CharField(max_length=5000)
    index = models.IntegerField()
    item = models.ForeignKey('items.Item', on_delete=models.CASCADE)
    slug = models.SlugField(editable=False, max_length=200, blank=True)

    def save(self, *args, **kwargs):
        slug = f"{slugify(self.item.flow.title)}.{slugify(self.item.name)}.{slugify(self.description)}"
        self.slug = slug
        super(Element, self).save(*args, **kwargs)

    def to_dict(self):
        return {
            "id": self.element_id,
            "description": self.description,
            "slug": self.slug,
            "type": self.type,
            "content": self.content,
            "index": self.index,
            "itemId": self.item.item_id,
        }

    def __str__(self):
        return self.slug
