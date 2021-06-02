package team.getherfolg.capstone.ui.auth.signup

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
import team.getherfolg.capstone.databinding.ActivitySignUpBinding
import team.getherfolg.capstone.network.SuitableClient
import team.getherfolg.capstone.ui.auth.login.LogInActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        signUpBinding.apply {
            btnRegister.setOnClickListener {
                val fullname = etFullName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                when {
                    fullname.isEmpty() -> {
                        etFullName.error = "Full name must be filled"
                        etFullName.requestFocus()
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
                    else -> {
                        val data = RegisterRequest(fullname, email, password)
                        registerUser(data)
                    }
                }
            }
        }
    }

    private fun registerUser(registerRequest: RegisterRequest) {
        SuitableClient.getService()
            .createAccount(registerRequest)
            .enqueue(object : Callback<RegisterRequest> {
                override fun onResponse(
                    call: Call<RegisterRequest>,
                    response: Response<RegisterRequest>
                ) {
                    Toast.makeText(this@SignUpActivity, "Register Success", Toast.LENGTH_SHORT)
                        .show()

                    Intent(this@SignUpActivity, LogInActivity::class.java).also {
                        it.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }

                override fun onFailure(call: Call<RegisterRequest>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity, "Register Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}