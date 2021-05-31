package team.getherfolg.capstone.data.entity

import com.google.gson.annotations.SerializedName

data class RegisterEntity(
    @SerializedName("fullname")
    val fullName: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("pwd")
    val password: String
)
