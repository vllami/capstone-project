from django.contrib import admin
from django.urls import path
from django.conf.urls import url, include
from userprofile.views import JobViewSet, user_profileViewSet, FileUploadViewSet, Image_companyViewSet, Image_userViewSet
from rest_framework.routers import DefaultRouter
from rest_framework.urlpatterns import format_suffix_patterns
from django.conf import settings
from django.conf.urls.static import static


urlpatterns = [
    path('auth/', include('auth.urls')),
    url(r'^admin/', admin.site.urls),
    path('joblist/', JobViewSet),
    path('upload/', FileUploadViewSet),
    path('userprofile/', user_profileViewSet),
    path('imagecompany/', Image_companyViewSet),
    path('imageuser/', Image_userViewSet),

]
if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL,
                          document_root=settings.MEDIA_ROOT)
