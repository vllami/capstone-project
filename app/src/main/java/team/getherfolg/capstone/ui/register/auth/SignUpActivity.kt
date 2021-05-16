package team.getherfolg.capstone.ui.register.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import team.getherfolg.capstone.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    companion object {
        const val TAG = "Register"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        signUpBinding.toolbar.setNavigationOnClickListener { onBackPressed() }

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        signUpBinding.btnRegister.setOnClickListener {
            val email = signUpBinding.etEmail.text.toString()
            val password = signUpBinding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Data shouldn't be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        Toast.makeText(
                            this,
                            "createAccountWithEmail: onComplete" + task.isSuccessful,
                            Toast.LENGTH_SHORT
                        ).show()

                        if (!task.isSuccessful) {
                            Toast.makeText(this, "Account Not Created", Toast.LENGTH_SHORT).show()
                            return@OnCompleteListener
                        } else {
                            val newUser = hashMapOf(
                                "E-mail" to email,
                                "Password" to password
                            )
                            db.collection("users").add(newUser)
                                .addOnSuccessListener {
                                    Log.i(TAG, "Sign up success, will be save to ${it.id}")
                                }
                                .addOnFailureListener {
                                    Log.i(TAG, "Sign up failed, ${it.message}")
                                }

                            Intent(this, LoginActivity::class.java).also {
                                startActivity(it)
                            }
                            finish()
                        }
                    })
                    .addOnFailureListener { exception ->
                        Log.e("error", exception.message.toString() )
                    }
            }
        }
    }
}