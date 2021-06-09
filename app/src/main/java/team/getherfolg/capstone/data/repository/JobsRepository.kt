package team.getherfolg.capstone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import team.getherfolg.capstone.data.entity.JobsEntity
import team.getherfolg.capstone.data.response.JobListResponse
import team.getherfolg.capstone.data.source.JobListDataSource
import team.getherfolg.capstone.data.source.RemoteDataSource

class JobsRepository private constructor(private val remoteDataSource: RemoteDataSource) : JobListDataSource {

    override fun loadListJob(): LiveData<List<JobsEntity>> {
        val getListJob = MutableLiveData<List<JobsEntity>>()

        remoteDataSource.getListJob(object : RemoteDataSource.LoadListJobCallback {
            override fun onAllJobListReceived(jobList: List<JobListResponse>?) {
                val listJob = ArrayList<JobsEntity>()

                if (jobList != null) {
                    for (jobs in jobList) {
                        jobs.apply {
                            val job = JobsEntity(companyName, categoryName, jobName)
                            listJob.add(job)
                        }
                    }
                    getListJob.postValue(listJob)
                }
            }
        })
        return getListJob
    }

    companion object {
        @Volatile
        private var instance: JobsRepository? = null

        fun getInstance(remote: RemoteDataSource): JobsRepository =
            instance ?: synchronized(this) {
                JobsRepository(remote).apply {
                    instance = this
                }
            }
    }

}