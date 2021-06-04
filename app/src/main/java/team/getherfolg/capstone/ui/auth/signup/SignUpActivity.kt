package team.getherfolg.capstone.ui.auth.signup

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import team.getherfolg.capstone.databinding.ActivitySignUpBinding
import team.getherfolg.capstone.ui.auth.login.LogInActivity
import java.util.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var activitySignUpBinding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var fStore: FirebaseFirestore

    private var userID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(activitySignUpBinding.root)

        mAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        db = FirebaseDatabase.getInstance()

        activitySignUpBinding.apply {
            toolbar.setNavigationOnClickListener { onBackPressed() }
            btnRegister.setOnClickListener {
                showControl(false)
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
                        registerUser(fullname, email, password)
                        showControl(true)
                    }
                }
            }
        }
    }

    private fun registerUser(fullname: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val toast = Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0, 9)
                    toast.show()

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
                    val toast = Toast.makeText(this, "This email has been registered", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0, 9)
                    toast.show()
                    showControl(false)
                }
            }
    }

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