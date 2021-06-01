package team.getherfolg.capstone.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.data.entity.JobListEntity
import team.getherfolg.capstone.network.SuitableClient

class MainViewModel : ViewModel() {

    fun postPDF(id: Int): List<JobListEntity> {
        val client = SuitableClient.getService()
        val result = client.getChooseFile(id)
        result.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                if (response.isSuccessful) response.body()?.results
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                Log.d("chooseFile", t.toString())
            }

        })
        return emptyList()
    }
}