package team.getherfolg.capstone.network

import com.squareup.okhttp.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface SuitableService {

    // ini bisa
    @Multipart
    @POST("/upload")
    fun uploadPDF(
        @Part("dataupload") dataupload: String?
    ): Call<ArrayList<RequestBody>>

//     ini bisa
//    @POST("/upload")
//    fun uploadPDF(
//        @Body dataupload: String?
//    ): Call<ArrayList<RequestBody>>
}