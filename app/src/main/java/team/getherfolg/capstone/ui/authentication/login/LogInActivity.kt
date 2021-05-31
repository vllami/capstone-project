package team.getherfolg.capstone.ui.authentication.login

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.data.remote.response.login.LoginRequest
import team.getherfolg.capstone.data.remote.response.login.LoginResponse
import team.getherfolg.capstone.databinding.ActivityLogInBinding
import team.getherfolg.capstone.network.SuitableClient
import team.getherfolg.capstone.ui.main.MainActivity

class LogInActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLogInBinding
    private lateinit var loginRequest: LoginRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.toolbar.setNavigationOnClickListener { onBackPressed() }

        loginBinding.apply {
            btnLogin.setOnClickListener {
                val username = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()

                when {
                    username.isEmpty() -> {
                        inputUsername.error = "Email must be filled"
                        return@setOnClickListener
                    }
                    password.isEmpty() || password.length < 6 -> {
                        inputPassword.error = "Minimum of password is 6 characters"
                        return@setOnClickListener
                    }
                    else -> loginUser(loginRequest)
                }
            }
        }
    }

    private fun loginUser(loginRequest: LoginRequest) {
        SuitableClient.getService().userLogin(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@LogInActivity, "Login Success", Toast.LENGTH_SHORT)
                            .show()

                        Intent(this@LogInActivity, MainActivity::class.java).also {
                            it.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(it)
                        }
                    } else {
                        Toast.makeText(this@LogInActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LogInActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                }

            })

    }
}