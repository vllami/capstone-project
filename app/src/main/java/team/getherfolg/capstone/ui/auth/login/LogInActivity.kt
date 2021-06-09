package team.getherfolg.capstone.ui.auth.login

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.data.form.LoginUserResponse
import team.getherfolg.capstone.databinding.ActivityLogInBinding
import team.getherfolg.capstone.networking.SuitableClient
import team.getherfolg.capstone.ui.main.MainActivity
import team.getherfolg.capstone.ui.preference.Constant
import team.getherfolg.capstone.ui.preference.PreferenceHelper

class LogInActivity : AppCompatActivity() {

    private lateinit var activityLogInBinding: ActivityLogInBinding
    private lateinit var prefHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLogInBinding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(activityLogInBinding.root)

        prefHelper = PreferenceHelper(this)

        activityLogInBinding.apply {
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }

            btnLogin.setOnClickListener {
                showControl(false)
                val username = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()

                when {
                    username.isEmpty() -> {
                        inputUsername.error = "Username must be filled"
                        inputUsername.requestFocus()
                        return@setOnClickListener
                    }
                    password.isEmpty() || password.length < 6 -> {
                        inputPassword.error = "Minimum of password is 6 characters"
                        inputPassword.requestFocus()
                        return@setOnClickListener
                    }
                    else -> {
                        saveSession()
                        val loginUser = LoginUserResponse(username, password)
                        userLogin(loginUser)
                        showControl(true)
                    }
                }
            }
        }
    }

    private fun saveSession() {
        prefHelper.putDataBoolean(Constant.PREF_IS_LOGIN, true)
    }

    private fun userLogin(loginUser: LoginUserResponse) {
        SuitableClient.getService().loginUser(loginUser)
            .enqueue(object : Callback<LoginUserResponse> {
                override fun onResponse(
                    call: Call<LoginUserResponse>,
                    response: Response<LoginUserResponse>
                ) {
                    Toast.makeText(
                        this@LogInActivity,
                        "Login Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    Intent(this@LogInActivity, MainActivity::class.java).also {
                        it.flags = FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK
                        startActivity(it)
                    }
                }

                override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
                    Toast.makeText(
                        this@LogInActivity,
                        "Username or password is wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

    private fun showControl(state: Boolean) {
        activityLogInBinding.apply {
            when {
                state -> {
                    progressBar.visibility = View.VISIBLE
                    imgLogin.visibility = View.GONE
                }
                else -> {
                    progressBar.visibility = View.GONE
                    imgLogin.visibility = View.VISIBLE
                }
            }
        }
    }
}