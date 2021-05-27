package team.getherfolg.capstone.ui.authentication.signup

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import team.getherfolg.capstone.databinding.ActivitySignUpBinding
import team.getherfolg.capstone.ui.authentication.login.LogInActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        signUpBinding.toolbar.setNavigationOnClickListener { onBackPressed() }

        mAuth = FirebaseAuth.getInstance()

        signUpBinding.apply {
            btnRegister.setOnClickListener {
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
                    else -> register(email, password)
                }
            }
        }
    }

    private fun register(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                    Intent(this, LogInActivity::class.java).also { move ->
                        move.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(move)
                    }
                } else {
                    Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}