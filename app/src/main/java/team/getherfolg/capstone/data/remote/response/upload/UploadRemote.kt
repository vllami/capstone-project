package team.getherfolg.capstone.data.remote.response.upload

import com.google.gson.annotations.SerializedName

data class UploadData(
    @SerializedName("dataupload")
    val dataUpload: Int
)