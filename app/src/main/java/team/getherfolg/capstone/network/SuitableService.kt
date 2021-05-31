package team.getherfolg.capstone.network

import retrofit2.Call
import retrofit2.http.GET
import team.getherfolg.capstone.data.remote.response.register.RegisterResponse
import team.getherfolg.capstone.data.remote.response.login.LoginResponse
import team.getherfolg.capstone.data.remote.response.upload.UploadResponse
import team.getherfolg.capstone.data.remote.response.job_list.JobListResponse
import team.getherfolg.capstone.data.remote.response.profile.ProfileResponse
import team.getherfolg.capstone.data.remote.response.delete_pdf.DeletePDFResponse
import team.getherfolg.capstone.data.remote.response.logout.LogoutResponse

interface SuitableService {

    @GET("/register")
    fun getRegister(): Call<RegisterResponse>

    @GET("/login")
    fun getLogin(): Call<LoginResponse>

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