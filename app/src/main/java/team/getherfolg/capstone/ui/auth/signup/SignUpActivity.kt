package team.getherfolg.capstone.ui.auth.signup

import android.app.ActivityOptions
import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.transition.Visibility
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import team.getherfolg.capstone.databinding.ActivitySignUpBinding
import team.getherfolg.capstone.ui.auth.login.LogInActivity
import java.util.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var fStore: FirebaseFirestore

    private var userID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        mAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        db = FirebaseDatabase.getInstance()

        signUpBinding.apply {
            toolbar.setNavigationOnClickListener { onBackPressed() }
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
                        registerUser(fullname, email, password)
                    }
                }
            }
        }
    }

    private fun registerUser(fullname: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                    userID = mAuth.currentUser?.uid
                    val user = hashMapOf(
                        "fullName" to fullname
                    )

                    fStore.collection("users")
                        .add(user)
                        .addOnSuccessListener {
                            Log.d("Register User", "Document added with ID: ${it.id}")
                        }
                        .addOnFailureListener {
                            Log.w("Register User", "Error adding document ${it.message}")
                        }
                    startActivity(Intent(this, LogInActivity::class.java))
                } else {
                    Toast.makeText(
                        this,
                        "Register failed, User has been registered",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}