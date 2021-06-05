package team.getherfolg.capstone.data.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("dataupload")
    var dataUpload: String? = null
)