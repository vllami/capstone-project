package team.getherfolg.capstone.data.remote.response.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("results")
    val result: List<RegisterRemote>
)