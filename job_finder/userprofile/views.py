from django.shortcuts import render
from .serializers import JobSerializer, User_profileSerializer, Image_companySerializer, Image_userSerializer
from .models import Job, user_profile, Image_company, Image_user
from rest_framework.decorators import api_view, permission_classes
from rest_framework.response import Response
from rest_flex_fields import is_expanded
from rest_framework.permissions import IsAuthenticated, AllowAny
from django.contrib.auth import get_user_model


from rest_framework.parsers import FormParser, MultiPartParser
from rest_framework.viewsets import ModelViewSet
from .models import FileUpload
from .serializers import FileUploadSerializer


class FileUploadViewSet(ModelViewSet):
    permission_classes = [IsAuthenticated]
    queryset = FileUpload.objects.all()
    serializer_class = FileUploadSerializer
    parser_classes = (MultiPartParser, FormParser,)

    def perform_create(self, serializer):
        serializer.save(owner=self.request.user,
                        datafile=self.request.data.get('datafile'))


@api_view(['GET'])
@permission_classes((IsAuthenticated, ))
def Image_companyViewSet(request):
    image = Image_company.objects.all()
    serializer = Image_companySerializer(image, many=True)
    return Response(serializer.data)


@api_view(['GET'])
@permission_classes((IsAuthenticated, ))
def Image_userViewSet(request):
    image = Image_user.objects.all()
    serializer = Image_userSerializer(image, many=True)
    return Response(serializer.data)


@api_view(['GET'])
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
