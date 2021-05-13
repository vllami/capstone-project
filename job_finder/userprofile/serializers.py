from .models import Job, user_profile, FileUpload, Category, Company, Image_company, Image_user
from rest_framework import serializers
from versatileimagefield.serializers import VersatileImageFieldSerializer


class Image_userSerializer(serializers.ModelSerializer):
    class Meta:
        model = Image_user
        fields = '__all__'


class Image_companySerializer(serializers.ModelSerializer):
    class Meta:
        model = Image_company
        fields = '__all__'


class CategorySerializer(serializers.ModelSerializer):
    class Meta:
        model = Category
        fields = ['pk', 'name']


class CompanySerializer(serializers.ModelSerializer):
    category = CategorySerializer(many=True)

    class Meta:
        model = Company
        fields = ['pk', 'name', 'category']


class FileUploadSerializer(serializers.HyperlinkedModelSerializer):
    owner = serializers.SlugRelatedField(
        read_only=True,
        slug_field='id'
    )

    class Meta:
        model = FileUpload
        read_only_fields = ('created', 'datafile', 'owner')


class JobSerializer(serializers.ModelSerializer):

    class Meta:
        model = Job
        fields = ['pk', 'name', 'category', 'company', 'desc_job']


class User_profileSerializer (serializers.ModelSerializer):
    class Meta:
        model = user_profile
        fields = '__all__'
