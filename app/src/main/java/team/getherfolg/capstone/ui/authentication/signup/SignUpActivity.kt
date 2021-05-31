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
import team.getherfolg.capstone.data.remote.response.register.RegisterResponse
import team.getherfolg.capstone.data.storage.SharedPrefManager
import team.getherfolg.capstone.databinding.ActivitySignUpBinding
import team.getherfolg.capstone.network.SuitableClient
import team.getherfolg.capstone.ui.main.MainActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        signUpBinding.toolbar.setNavigationOnClickListener { onBackPressed() }

        signUpBinding.apply {
            btnRegister.setOnClickListener {
                val fullName = etFullName.text.toString().trim()
                val username = etUsername.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()


                when {
                    fullName.isEmpty() -> {
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
                    else -> register(fullName, username, email, password)
                }
            }
        }
    }

    private fun register(fullName: String, username: String, email: String, password: String) {
        SuitableClient.getService().createAccount(fullName, username, email, password)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    Toast.makeText(
                        this@SignUpActivity,
                        response.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    override fun onStart() {
        super.onStart()

        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            Intent(this, MainActivity::class.java).also { move ->
                move.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                startActivity(move)
            }
        }
    }
}