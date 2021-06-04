package team.getherfolg.capstone.data

import com.google.gson.annotations.SerializedName

data class ResultsCVScan(
    @SerializedName("cat_id")
    val id: Int,
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("create_on")
    val createOn: String,
    @SerializedName("expire_on")
    val expireOn: String,
    @SerializedName("job_detail")
    val jobDetail: String,
    @SerializedName("job_id")
    val jobId: Int,
    @SerializedName("job_name")
    val jobName: String
)
