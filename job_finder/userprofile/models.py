from django.db import models
from django.contrib.auth.models import User
from versatileimagefield.fields import VersatileImageField, PPOIField


class Image(models.Model):
    name = models.CharField(max_length=255)
    image = VersatileImageField(
        'Image',
        upload_to='images/',
        ppoi_field='image_ppoi'
    )
    image_ppoi = PPOIField()

    def __str__(self):
        return self.name


class Company(models.Model):
    name = models.CharField(max_length=255)
    url = models.TextField()

    def __str__(self):
        return self.name


class Category(models.Model):
    name = models.CharField(max_length=255)

    def __str__(self):
        return self.name


class Job(models.Model):
    name = models.CharField(max_length=255)
    desc_job = models.TextField()
    skil_req = models.TextField()
    category = models.ManyToManyField(Category, related_name='products')
    created = models.DateField(auto_now_add=True)
    updated = models.DateField(auto_now=True)
    company = models.ForeignKey(
        Company, on_delete=models.CASCADE, related_name='sites', related_query_name='site')

    class Meta:
        ordering = ['-created']

    def __str__(self):
        return self.name


class user_profile(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE,
                             related_name='comments', related_query_name='comment')
    full_name = models.CharField(max_length=255)
    phone_number = models.CharField(max_length=255)
    country = models.CharField(max_length=255)
    skill = models.TextField()
    address = models.TextField()
    created = models.DateField(auto_now_add=True)
    updated = models.DateField(auto_now=True)
    image = models.ManyToManyField(
        Image, related_name='products')

    def __str__(self):
        return self.title
