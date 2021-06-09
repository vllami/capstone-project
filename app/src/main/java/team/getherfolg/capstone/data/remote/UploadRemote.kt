package team.getherfolg.capstone.data.remote

import com.google.gson.annotations.SerializedName

data class UploadRemote(
    @SerializedName("category_name")
    val categoryName: String? = null,
    @SerializedName("company_id")
    val companyId: Int? = null,
    @SerializedName("create_on")
    val createOn: String? = null,
    @SerializedName("comp_id")
    val compId: Int? = null,
    @SerializedName("job_detail")
    val jobDetail: String? = null,
    @SerializedName("category_id")
    val categoryId: Int? = null,
    @SerializedName("job_name")
    val jobName: String? = null,
    @SerializedName("phone")
    val phone: Any? = null,
    @SerializedName("expire_on")
    val expireOn: String? = null,
    @SerializedName("job_id")
    val jobId: Int? = null,
    @SerializedName("company_name")
    val companyName: String? = null,
    @SerializedName("cat_id")
    val catId: Int? = null,
    @SerializedName("company_detail")
    val companyDetail: String? = null,
    @SerializedName("email")
    val email: Any? = null
)