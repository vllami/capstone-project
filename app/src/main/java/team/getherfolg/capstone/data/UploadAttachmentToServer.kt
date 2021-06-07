@file:Suppress("DEPRECATION")

package team.getherfolg.capstone.data

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.networking.SuitableClient
import java.io.File

class UploadAttachmentToServer(
    private val filePath: String,
    private val pdfName: String,
    private val context: Context
) {
    var urlAttachment: String? = null

    lateinit var progressDialog: ProgressDialog

    fun uploadAttachment(): Boolean {
        var status = false

        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please wait")

        if (filePath != null && filePath.isNotEmpty()) {
            val file = File(filePath)

            val requestBody = RequestBody.create("/".toMediaTypeOrNull(), file)
            val fileToUpload =
                MultipartBody.Part.createFormData("cv", file.name, requestBody)
//            val fileName: RequestBody = RequestBody.create(
//                "text/plain".toMediaTypeOrNull(),
//                pdfName
//            )

            SuitableClient.getService().uploadPDF(fileToUpload)
                .enqueue(object : Callback<JobListResultResponse> {
                    override fun onResponse(
                        call: Call<JobListResultResponse>,
                        response: Response<JobListResultResponse>
                    ) {
                        progressDialog.dismiss()
                        Log.d("On Response: Success", response.toString())
                        Toast.makeText(context, "Upload Success", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<JobListResultResponse>, t: Throwable) {
                        Log.e("Upload", "On Failed : ${t.message.toString()}")
                    }
                })
        }
        return status
    }
}