package team.getherfolg.capstone.data.remote.response.login

data class LoginResponse(
    val id: Int,
    val fullname: String,
    val username: String,
    val email: String,
    val password: String
)