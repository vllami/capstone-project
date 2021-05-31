package team.getherfolg.capstone.ui.authentication.signup

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.data.remote.response.register.RegisterRequest
import team.getherfolg.capstone.data.remote.response.register.RegisterResponse
import team.getherfolg.capstone.databinding.ActivitySignUpBinding
import team.getherfolg.capstone.network.SuitableClient
import team.getherfolg.capstone.ui.authentication.login.LogInActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var registerRequest: RegisterRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        signUpBinding.toolbar.setNavigationOnClickListener { onBackPressed() }

        signUpBinding.apply {
            btnRegister.setOnClickListener {
                val fullname = etFullName.text.toString().trim()
                val username = etUsername.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                when {
                    fullname.isEmpty() -> {
                        etFullName.error = "Full name must be filled"
                        etFullName.requestFocus()
                        return@setOnClickListener
                    }
                    username.isEmpty() -> {
                        etUsername.error = "Username must be filled"
                        etUsername.requestFocus()
                        return@setOnClickListener
                    }
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
                    else -> registerUser(registerRequest)
                }
            }
        }
    }

    private fun registerUser(registerRequest: RegisterRequest) {
        SuitableClient.getService()
            .createAccount(registerRequest)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SignUpActivity, "Register Success", Toast.LENGTH_SHORT)
                            .show()

                        Intent(this@SignUpActivity, LogInActivity::class.java).also {
                            it.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(it)
                        }
                    } else {
                        Toast.makeText(this@SignUpActivity, "Register Failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Register Failed", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }
}