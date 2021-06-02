package team.getherfolg.capstone.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import team.getherfolg.capstone.data.remote.response.delete_pdf.DeletePDFResponse
import team.getherfolg.capstone.data.remote.response.job_list.JobListResponse
import team.getherfolg.capstone.data.remote.response.login.LoginRequest
import team.getherfolg.capstone.data.remote.response.profile.ProfileResponse
import team.getherfolg.capstone.data.remote.response.register.RegisterRequest
import team.getherfolg.capstone.data.remote.response.upload.UploadResponse

interface SuitableService {

    @POST("/register")
    fun createAccount(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterRequest>

    @POST("/login")
    fun userLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginRequest>

    @GET("/logout")
    fun userLogout(): Call<LoginRequest>

    @POST("/upload")
    fun postPDF(
        @Part("dataupload") dataUpload: MultipartBody.Part
    ): Call<RequestBody>

    @GET("/joblist")
    fun getJobList(): Call<JobListResponse>

    @GET("/profile")
    fun getProfile(): Call<ProfileResponse>

    @GET("/deletepdf")
    fun getDeletePDF(): Call<DeletePDFResponse>

}