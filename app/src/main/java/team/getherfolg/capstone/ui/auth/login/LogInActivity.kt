package team.getherfolg.capstone.ui.auth.login

import android.content.Intent
import android.content.Intent.*
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import team.getherfolg.capstone.databinding.ActivityLogInBinding
import team.getherfolg.capstone.ui.main.MainActivity
import team.getherfolg.capstone.ui.main.home.profile.ChangePasswordActivity

class LogInActivity : AppCompatActivity() {

    private lateinit var activityLogInBinding: ActivityLogInBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLogInBinding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(activityLogInBinding.root)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        activityLogInBinding.apply {
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }

            btnLogin.setOnClickListener {
                showControl(false)
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                when {
                    email.isEmpty() -> {
                        inputEmail.error = "Email must be filled"
                        inputEmail.requestFocus()
                        return@setOnClickListener
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        inputEmail.error = "Please type the correct email format"
                        inputEmail.requestFocus()
                        return@setOnClickListener
                    }
                    password.isEmpty() || password.length < 6 -> {
                        inputPassword.error = "Minimum of password is 6 characters"
                        inputPassword.requestFocus()
                        return@setOnClickListener
                    }
                    else -> {
                        loginUser(email, password)
                        showControl(true)
                    }
                }

                when {
                    email.isNotEmpty() -> inputEmail.error = null
                    password.isNotEmpty() -> inputPassword.error = null
                }
            }

            tvForgot.setOnClickListener {
                startActivity(Intent(this@LogInActivity, ChangePasswordActivity::class.java))
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val verif = mAuth.currentUser

                    if (verif != null)
                        when {
                            verif.isEmailVerified -> {
                                val toast = Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT)
                                toast.setGravity(Gravity.TOP, 0, 9)
                                toast.show()
                                startActivity(Intent(this, MainActivity::class.java))
                            }
                            else -> {
                                verif.sendEmailVerification()
                                val toast = Toast.makeText(this, "Open your email for verification", Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.TOP, 0, 9)
                                toast.show()
                                showControl(false)
                            }
                        }
                } else {
                    val toast = Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0, 9)
                    toast.show()
                    showControl(false)
                }
            }
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