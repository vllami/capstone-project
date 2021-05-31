package team.getherfolg.capstone.data.remote.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("results")
    val result: List<LoginRemote>
)