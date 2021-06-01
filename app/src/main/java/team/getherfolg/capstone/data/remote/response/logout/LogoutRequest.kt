package team.getherfolg.capstone.data.remote.response.logout

data class LogoutRequest(
    val fullname: String,
    val username: String,
    val email: String,
    val pwd: String
)
