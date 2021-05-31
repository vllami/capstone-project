package team.getherfolg.capstone.data.remote.response.login

import team.getherfolg.capstone.data.User

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val user: User
)