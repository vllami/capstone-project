package team.getherfolg.capstone.data.remote.response.job_list

import com.google.gson.annotations.SerializedName

data class JobListResponse(
    @SerializedName("results")
    val result: List<JobListRemote>
)