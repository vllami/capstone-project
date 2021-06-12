package team.getherfolg.capstone.networking

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import team.getherfolg.capstone.data.form.LoginUserResponse
import team.getherfolg.capstone.data.form.RegisterUserResponse
import team.getherfolg.capstone.data.response.JobListResponse
import team.getherfolg.capstone.data.response.UploadResponse

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

    @POST("/upload")
    fun upload(
        @Body dataUpload: RequestBody
    ): Call<UploadResponse>

    @GET("/joblist")
    fun getAllListJob(): Call<List<JobListResponse>>

}