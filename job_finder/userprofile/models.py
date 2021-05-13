from django.db import models
from django.contrib.auth.models import User
from versatileimagefield.fields import VersatileImageField, PPOIField


class FileUpload(models.Model):
    created = models.DateTimeField(auto_now_add=True)
    owner = models.OneToOneField(User, to_field='id', on_delete=models.CASCADE)
    datafile = models.FileField()


class Category(models.Model):
    name = models.CharField(max_length=255)

    def __str__(self):
        return self.name


class Company(models.Model):
    name = models.CharField(max_length=255)
    url = models.TextField()

    def __str__(self):
        return self.name


class Image_user(models.Model):
    name = models.CharField(max_length=255)
    user = models.OneToOneField(
        User, on_delete=models.CASCADE, to_field='id', null=True)

    image = VersatileImageField(
        'Image',
        upload_to='images/user',
        ppoi_field='image_ppoi'
    )
    image_ppoi = PPOIField()

    def __str__(self):
        return self.name


class Image_company(models.Model):
    name = models.CharField(max_length=255)
    company = models.OneToOneField(
        Company, on_delete=models.CASCADE, to_field='id', null=True)

    image = VersatileImageField(
        'Image',
        upload_to='images/company',
        ppoi_field='image_ppoi'
    )
    image_ppoi = PPOIField()

    def __str__(self):
        return self.name


class Job(models.Model):
    name = models.CharField(max_length=255)
    desc_job = models.TextField()
    skil_req = models.TextField()
    category = models.ManyToManyField(Category)
    created = models.DateField(auto_now_add=True)
    updated = models.DateField(auto_now=True)
    image_company = models.OneToOneField(
        Image_company, on_delete=models.CASCADE, to_field='id')
    company = models.ForeignKey(
        Company, on_delete=models.CASCADE)

    class Meta:
        ordering = ['-created']

    def __str__(self):
        return self.name


class user_profile(models.Model):
    #user = models.OneToOneField(User, on_delete=models.CASCADE)
    user = models.OneToOneField(User, on_delete=models.CASCADE, to_field='id')
    full_name = models.CharField(max_length=255)
    phone_number = models.CharField(max_length=255)
    country = models.CharField(max_length=255)
    skill = models.TextField()
    address = models.TextField()
    created = models.DateField(auto_now_add=True)
    updated = models.DateField(auto_now=True)
    user_img = models.OneToOneField(
        Image_user, on_delete=models.CASCADE, to_field='id')

    def __str__(self):
        return self.full_name
