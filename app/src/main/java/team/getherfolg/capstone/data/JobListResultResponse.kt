package team.getherfolg.capstone.data

import com.google.gson.annotations.SerializedName

data class JobListResultResponse(

	@field:SerializedName("JobListResultResponse")
	val jobListResultResponse: List<JobListResultResponseItem?>? = null
)

data class JobListResultResponseItem(

	@field:SerializedName("category_name")
	val categoryName: String? = null,

	@field:SerializedName("company_id")
	val companyId: Int? = null,

	@field:SerializedName("create_on")
	val createOn: String? = null,

	@field:SerializedName("comp_id")
	val compId: Int? = null,

	@field:SerializedName("job_detail")
	val jobDetail: String? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("job_name")
	val jobName: String? = null,

	@field:SerializedName("phone")
	val phone: Any? = null,

	@field:SerializedName("expire_on")
	val expireOn: String? = null,

	@field:SerializedName("job_id")
	val jobId: Int? = null,

	@field:SerializedName("company_name")
	val companyName: String? = null,

	@field:SerializedName("cat_id")
	val catId: Int? = null,

	@field:SerializedName("company_detail")
	val companyDetail: String? = null,

	@field:SerializedName("email")
	val email: Any? = null
)
