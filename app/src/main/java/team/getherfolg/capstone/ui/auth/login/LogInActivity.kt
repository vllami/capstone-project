package team.getherfolg.capstone.ui.auth.login

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.data.remote.response.login.LoginRequest
import team.getherfolg.capstone.databinding.ActivityLogInBinding
import team.getherfolg.capstone.network.SuitableClient
import team.getherfolg.capstone.ui.main.MainActivity

class LogInActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                when {
                    email.isEmpty() -> {
                        inputEmail.error = "Email must be filled"
                        return@setOnClickListener
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        inputEmail.error = "Email is not valid"
                        return@setOnClickListener
                    }
                    password.isEmpty() || password.length < 6 -> {
                        inputPassword.error = "Minimum of password is 6 characters"
                        return@setOnClickListener
                    }
                    else -> {
                        val data = LoginRequest(email, password)
                        loginUser(data)
                    }
                }
            }
        }
    }

    private fun loginUser(loginRequest: LoginRequest) {
        SuitableClient.getService().userLogin(loginRequest)
            .enqueue(object : Callback<LoginRequest> {
                override fun onResponse(
                    call: Call<LoginRequest>,
                    response: Response<LoginRequest>
                ) {
                    Toast.makeText(this@LogInActivity, "Login Success", Toast.LENGTH_SHORT)
                        .show()

                    Intent(this@LogInActivity, MainActivity::class.java).also {
                        it.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }

                override fun onFailure(call: Call<LoginRequest>, t: Throwable) {
                    Toast.makeText(this@LogInActivity, "Login Failed", Toast.LENGTH_SHORT)
                        .show()
                }


            })

    }
}