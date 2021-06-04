package team.getherfolg.capstone.data.upload

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("dataupload")
    var dataUpload: String? = null
)