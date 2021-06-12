package team.getherfolg.capstone.data.response

import com.google.gson.annotations.SerializedName
import team.getherfolg.capstone.data.remote.UploadRemote

data class UploadResponse(
	@SerializedName("UploadResponse")
	val uploadResponse: List<UploadRemote?>? = null
)