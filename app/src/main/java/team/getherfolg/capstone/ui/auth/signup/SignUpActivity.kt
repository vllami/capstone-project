package team.getherfolg.capstone.ui.auth.signup

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.data.form.RegisterUserResponse
import team.getherfolg.capstone.databinding.ActivitySignUpBinding
import team.getherfolg.capstone.networking.SuitableClient
import team.getherfolg.capstone.ui.auth.login.LogInActivity
import team.getherfolg.capstone.ui.preference.Constant
import team.getherfolg.capstone.ui.preference.PreferenceHelper
import java.util.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var activitySignUpBinding: ActivitySignUpBinding
    private lateinit var prefHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(activitySignUpBinding.root)

        prefHelper = PreferenceHelper(this)

        activitySignUpBinding.apply {
            toolbar.setNavigationOnClickListener { onBackPressed() }
            btnRegister.setOnClickListener {
                showControl(false)
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
                        inputEmail.requestFocus()
                        return@setOnClickListener
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        inputEmail.error = "Email is not valid"
                        inputEmail.requestFocus()
                        return@setOnClickListener
                    }
                    password.isEmpty() || password.length < 6 -> {
                        inputPassword.error = "Minimum of password is 6 characters"
                        inputPassword.requestFocus()
                        return@setOnClickListener
                    }
                    else -> {
                        val register = RegisterUserResponse(fullname, username, email, password)
                        registerUser(register)
                        showControl(true)
                    }
                }
            }
        }
    }

    private fun registerUser(register: RegisterUserResponse) {
        SuitableClient.getService().registerUser(register)
            .enqueue(object : Callback<RegisterUserResponse> {
                override fun onResponse(
                    call: Call<RegisterUserResponse>,
                    response: Response<RegisterUserResponse>
                ) {
                    Toast.makeText(this@SignUpActivity, "Register Success", Toast.LENGTH_SHORT)
                        .show()

                    Intent(this@SignUpActivity, LogInActivity::class.java).also {
                        it.flags = FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK
                        startActivity(it)
                    }
                }

                override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity, "Register Failed", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

    @Suppress("SameParameterValue")
    private fun showControl(state: Boolean) {
        activitySignUpBinding.apply {
            when {
                state -> {
                    progressBar.visibility = View.VISIBLE
                    imgRegister.visibility = View.GONE
                }
                else -> {
                    progressBar.visibility = View.GONE
                    imgRegister.visibility = View.VISIBLE
                }
            }
        }
    }
}