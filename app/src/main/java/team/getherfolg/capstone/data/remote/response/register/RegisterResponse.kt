package team.getherfolg.capstone.data.remote.response.register

data class RegisterResponse(
    val id: Int,
    val fullname: String,
    val username: String,
    val email: String
)