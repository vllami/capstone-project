package team.getherfolg.capstone.ui.register.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import team.getherfolg.capstone.databinding.ActivityLoginBinding
import team.getherfolg.capstone.ui.main.MainActivity
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.toolbar.setNavigationOnClickListener { onBackPressed() }

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

//        loginBinding.btnLogin.setOnClickListener {
//            setLogin()
//        }
        loginBinding.btnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

//    private fun setLogin() {
//        val email = loginBinding.etEmail.text.toString()
//        val password = loginBinding.etPassword.text.toString()
//
//        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
//            if (it.isSuccessful) {
//                Toast.makeText(this, "Log in success", Toast.LENGTH_SHORT).show()
//                authSuccesfull(email)
//            } else {
//                Toast.makeText(this, "Log in failed", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun authSuccesfull(email: String) {
//        db.collection("users").whereEqualTo("E-mail", email).get()
//            .addOnSuccessListener {
//                Intent(this, MainActivity::class.java).also {
//                    startActivity(it)
//                }
//            }
//    }
}