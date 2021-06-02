package team.getherfolg.capstone.ui.main.home.profile

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import team.getherfolg.capstone.databinding.ActivityProfileBinding
import team.getherfolg.capstone.ui.auth.AuthActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var activityProfileBinding: ActivityProfileBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(activityProfileBinding.root)

        mAuth = FirebaseAuth.getInstance()

        activityProfileBinding.btnLogout.setOnClickListener {
            mAuth.signOut()
            Toast.makeText(this, "Log out Success", Toast.LENGTH_SHORT).show()
            Intent(this, AuthActivity::class.java).also {
                it.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }

}