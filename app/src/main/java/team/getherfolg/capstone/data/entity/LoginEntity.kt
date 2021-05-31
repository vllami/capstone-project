package team.getherfolg.capstone.data.entity

import com.google.gson.annotations.SerializedName

data class LoginEntity(
    @SerializedName("username")
    val username: Int,
    @SerializedName("password")
    val password: Int
)