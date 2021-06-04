package team.getherfolg.capstone.networking

import com.squareup.okhttp.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface SuitableService {

    @Multipart
    @POST("/upload")
    fun uploadPDF(
        @Part("dataupload") dataupload: String?
    ): Call<ArrayList<RequestBody>>

}