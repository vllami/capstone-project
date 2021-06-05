package team.getherfolg.capstone.data.response

import com.google.gson.annotations.SerializedName

data class JobListResponse(

    @field:SerializedName("category_name")
    val categoryName: String,

    @field:SerializedName("company_id")
    val companyId: Int,

    @field:SerializedName("create_on")
    val createOn: String,

    @field:SerializedName("comp_id")
    val compId: Int,

    @field:SerializedName("job_detail")
    val jobDetail: String,

    @field:SerializedName("category_id")
    val categoryId: Int,

    @field:SerializedName("job_name")
    val jobName: String,

    @field:SerializedName("phone")
    val phone: Any,

    @field:SerializedName("expire_on")
    val expireOn: String,

    @field:SerializedName("job_id")
    val jobId: Int,

    @field:SerializedName("company_name")
    val companyName: String,

    @field:SerializedName("cat_id")
    val catId: Int,

    @field:SerializedName("company_detail")
    val companyDetail: String,

    @field:SerializedName("email")
    val email: Any

)