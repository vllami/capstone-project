package team.getherfolg.capstone.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import team.getherfolg.capstone.data.remote.response.delete_pdf.DeletePDFResponse
import team.getherfolg.capstone.data.remote.response.job_list.JobListResponse
import team.getherfolg.capstone.data.remote.response.login.LoginResponse
import team.getherfolg.capstone.data.remote.response.logout.LogoutResponse
import team.getherfolg.capstone.data.remote.response.profile.ProfileResponse
import team.getherfolg.capstone.data.remote.response.register.RegisterResponse
import team.getherfolg.capstone.data.remote.response.upload.UploadResponse

interface SuitableService {

    @FormUrlEncoded
    @POST("/register")
    fun createAccount(
        @Field("fullname") fullname: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("pwd") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/login")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("/upload")
    fun getChooseFile(): Call<UploadResponse>

    @GET("/joblist")
    fun getJobList(): Call<JobListResponse>

    @GET("/profile")
    fun getProfile(): Call<ProfileResponse>

    @GET("/deletepdf")
    fun getDeletePDF(): Call<DeletePDFResponse>

    @GET("/logout")
    fun getLogout(): Call<LogoutResponse>

}