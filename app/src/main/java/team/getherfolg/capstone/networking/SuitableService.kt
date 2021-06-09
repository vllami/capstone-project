package team.getherfolg.capstone.networking

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import team.getherfolg.capstone.data.response.UploadResponse
import team.getherfolg.capstone.data.form.LoginUserResponse
import team.getherfolg.capstone.data.form.RegisterUserResponse
import team.getherfolg.capstone.data.response.JobListResponse

interface SuitableService {

    @POST("/register")
    fun registerUser(
        @Body register: RegisterUserResponse
    ): Call<RegisterUserResponse>

    @POST("/login")
    fun loginUser(
        @Body login: LoginUserResponse
    ): Call<LoginUserResponse>

    @GET("/logout")
    fun logoutUser(): Call<LoginUserResponse>

    @Multipart
    @POST("/upload")
    fun upload(
        @Body dataupload: RequestBody
    ): Call<UploadResponse>

    @GET("/joblist")
    fun getAllListJob(): Call<List<JobListResponse>>

}