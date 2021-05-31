package team.getherfolg.capstone.network

import retrofit2.Call
import retrofit2.http.*
import team.getherfolg.capstone.data.remote.response.delete_pdf.DeletePDFResponse
import team.getherfolg.capstone.data.remote.response.job_list.JobListResponse
import team.getherfolg.capstone.data.remote.response.login.LoginRequest
import team.getherfolg.capstone.data.remote.response.login.LoginResponse
import team.getherfolg.capstone.data.remote.response.logout.LogoutResponse
import team.getherfolg.capstone.data.remote.response.profile.ProfileResponse
import team.getherfolg.capstone.data.remote.response.register.RegisterRequest
import team.getherfolg.capstone.data.remote.response.register.RegisterResponse
import team.getherfolg.capstone.data.remote.response.upload.UploadResponse

interface SuitableService {

    @FormUrlEncoded
    @POST("/register")
    fun createAccount(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/login")
    fun userLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("/upload")
    fun getChooseFile(
        @Field("id") id: Int
    ): Call<UploadResponse>

    @GET("/joblist")
    fun getJobList(): Call<JobListResponse>

    @GET("/profile")
    fun getProfile(): Call<ProfileResponse>

    @GET("/deletepdf")
    fun getDeletePDF(): Call<DeletePDFResponse>

    @GET("/logout")
    fun getLogout(): Call<LogoutResponse>

}