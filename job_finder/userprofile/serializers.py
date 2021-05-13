from .models import Job, user_profile, Image
from rest_framework import serializers
from versatileimagefield.serializers import VersatileImageFieldSerializer
from rest_framework_simplejwt.serializers import TokenObtainPairSerializer


class MyTokenObtainPairSerializer(TokenObtainPairSerializer):

    @classmethod
    def get_token(cls, user):
        token = super(MyTokenObtainPairSerializer, cls).get_token(user)

        # Add custom claims
        token['username'] = user.username
        return token


class JobSerializer(serializers.ModelSerializer):

    class Meta:
        model = Job
        fields = ['pk', 'name', 'category', 'desc_job', 'skil_req', 'company']


class User_profileSerializer (serializers.ModelSerializer):
    class Meta:
        model = user_profile
        fields = '__all__'
