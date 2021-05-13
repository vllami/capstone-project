from django.shortcuts import render
from .serializers import JobSerializer, User_profileSerializer
from .models import Job, user_profile
from rest_framework.decorators import api_view, permission_classes
from rest_framework.response import Response
from .serializers import MyTokenObtainPairSerializer
from rest_flex_fields import is_expanded
from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework_simplejwt.views import TokenObtainPairView


class MyObtainTokenPairView(TokenObtainPairView):
    permission_classes = (AllowAny)
    serializer_class = MyTokenObtainPairSerializer


@api_view(['GET'])
@permission_classes((IsAuthenticated, ))
def JobViewSet(request):
    job_listed = Job.objects.all()
    serializer = JobSerializer(job_listed, many=True)
    return Response(serializer.data)


@ api_view(['GET', 'POST', 'PUT'])
@permission_classes((IsAuthenticated, ))
def user_profileViewSet(request):
    """
    List all code snippets, or create a new snippet.
    """
    if request.method == 'GET':
        userprofile = user_profile.objects.all()
        serializer = User_profileSerializer(userprofile, many=True)
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = User_profileSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    elif request.method == 'PUT':
        serializer = User_profileSerializer(user_profile, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
