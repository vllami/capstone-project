package team.getherfolg.capstone.data.source

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.data.response.JobListResponse
import team.getherfolg.capstone.networking.SuitableClient

class RemoteDataSource {

    fun getListJob(callback: LoadListJobCallback) {
        SuitableClient.getService().getAllListJob()
            .enqueue(object : Callback<List<JobListResponse>> {
                override fun onResponse(
                    call: Call<List<JobListResponse>>,
                    response: Response<List<JobListResponse>>
                ) {
                    callback.onAllJobListReceived(response.body())
                }

                override fun onFailure(call: Call<List<JobListResponse>>, t: Throwable) {
                    Log.e(TAG, "Failure ${t.message}")
                }

            })
    }

    interface LoadListJobCallback {
        fun onAllJobListReceived(jobList: List<JobListResponse>?)
    }

    companion object {
        const val TAG = "Remote Data Source"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource = instance ?: synchronized(this) {
            RemoteDataSource().apply { instance = this }
        }
    }
}