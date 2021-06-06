package team.getherfolg.capstone.networking

import com.squareup.okhttp.RequestBody
import retrofit2.Call
import retrofit2.http.*
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
    fun uploadPDF(
        @Part("dataupload") dataupload: String?
    ): Call<ArrayList<RequestBody>>

    @GET("/joblist")
    fun getAllListJob(): Call<List<JobListResponse>>

}