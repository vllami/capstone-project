package team.getherfolg.capstone.data.remote.response.logout

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("results")
    val result: List<LogoutRemote>
)