package team.getherfolg.capstone.ui.main.home.profile

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import team.getherfolg.capstone.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var changePasswordBinding: ActivityChangePasswordBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changePasswordBinding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(changePasswordBinding.root)

        mAuth = FirebaseAuth.getInstance()

        changePasswordBinding.apply {
            toolbar.setNavigationOnClickListener { onBackPressed() }

            btnForgot.setOnClickListener {
                val email = etEmail.text.toString().trim()

                when {
                    email.isEmpty() -> {
                        inputEmail.error = "Email must be filled"
                        return@setOnClickListener
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        inputEmail.error = "Email is not valid"
                        return@setOnClickListener
                    }
                    else -> sendVerification(email)
                }
            }
        }
    }

    private fun sendVerification(email: String) {
        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Check your email to reset password", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Request failed, please try again !!", Toast.LENGTH_LONG).show()
                }
            }
    }
}