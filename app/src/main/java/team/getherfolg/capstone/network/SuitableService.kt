package team.getherfolg.capstone.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import team.getherfolg.capstone.data.remote.response.delete_pdf.DeletePDFResponse
import team.getherfolg.capstone.data.remote.response.job_list.JobListResponse
import team.getherfolg.capstone.data.remote.response.login.LoginRequest
import team.getherfolg.capstone.data.remote.response.profile.ProfileResponse
import team.getherfolg.capstone.data.remote.response.register.RegisterRequest
import team.getherfolg.capstone.data.remote.response.upload.UploadResponse

interface SuitableService {

    // register
    @POST("/register")
    fun createAccount(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterRequest>

    // login
    @POST("/login")
    fun userLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginRequest>

    // log out
    @GET("/logout")
    fun userLogout(): Call<LoginRequest>

    // upload
    @POST("/upload")
    fun sendFile(
        @Field("dataupload") encodeData: String
    ): Call<UploadResponse>

    @GET("/joblist")
    fun getJobList(): Call<JobListResponse>

    @GET("/profile")
    fun getProfile(): Call<ProfileResponse>

    @GET("/deletepdf")
    fun getDeletePDF(): Call<DeletePDFResponse>

}