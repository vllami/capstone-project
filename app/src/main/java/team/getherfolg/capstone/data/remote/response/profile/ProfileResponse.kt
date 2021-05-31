package team.getherfolg.capstone.data.remote.response.profile

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("results")
    val result: List<ProfileRemote>
)