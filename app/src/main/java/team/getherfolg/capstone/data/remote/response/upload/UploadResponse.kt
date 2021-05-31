package team.getherfolg.capstone.data.remote.response.upload

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("results")
    val result: List<UploadRemote>
)