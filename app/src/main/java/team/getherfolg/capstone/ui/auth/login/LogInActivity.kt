package team.getherfolg.capstone.ui.auth.login

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import team.getherfolg.capstone.databinding.ActivityLogInBinding
import team.getherfolg.capstone.ui.main.MainActivity
import team.getherfolg.capstone.ui.main.home.profile.ChangePasswordActivity

class LogInActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLogInBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        loginBinding.apply {
            toolbar.setNavigationOnClickListener { onBackPressed() }
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
                        loginUser(email, password)
                    }
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
                                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                            }
                            else -> {
                                verif.sendEmailVerification()
                                Toast.makeText(
                                    this,
                                    "Check your email to verification",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Your email or password was wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}