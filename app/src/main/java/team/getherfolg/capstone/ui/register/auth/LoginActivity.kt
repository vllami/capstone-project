package team.getherfolg.capstone.ui.register.auth

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import team.getherfolg.capstone.databinding.ActivityLoginBinding
import team.getherfolg.capstone.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.toolbar.setNavigationOnClickListener { onBackPressed() }

        mAuth = FirebaseAuth.getInstance()

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
                    else -> userLogIn(email, password)
                }
            }
        }
    }

    private fun userLogIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Intent(this, MainActivity::class.java).also { move ->
                        move.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(move)
                    }
                } else {
                    Toast.makeText(this, "Email or password incorrect", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser != null) {
            Intent(this, MainActivity::class.java).also { move ->
                move.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                startActivity(move)
            }
        }
    }
}