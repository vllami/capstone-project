from django.contrib import admin
from django.urls import path
from django.conf.urls import url, include
from userprofile.views import JobViewSet, user_profileViewSet
from rest_framework.routers import DefaultRouter
from rest_framework.urlpatterns import format_suffix_patterns
from django.conf import settings
from django.conf.urls.static import static


urlpatterns = [
    path('auth/', include('auth.urls')),
    url(r'^admin/', admin.site.urls),
    path('job_listed/', JobViewSet),
    path('job_listed/<int:pk>', JobViewSet),
    path('userprofile/', user_profileViewSet),
    path('userprofile/<int:pk>', user_profileViewSet),

]
if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL,
                          document_root=settings.MEDIA_ROOT)
