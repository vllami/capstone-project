package team.getherfolg.capstone.ui.main.home.profile

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import team.getherfolg.capstone.databinding.ActivityProfileBinding
import team.getherfolg.capstone.ui.splashscreen.SplashScreenActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var activityProfileBinding: ActivityProfileBinding
    private lateinit var fStore: FirebaseFirestore
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(activityProfileBinding.root)

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        fStore.collection("users")
            .get()
            .addOnSuccessListener {
                for (doc in it) {
                    activityProfileBinding.tvName.text = doc["fullName"].toString()
                    activityProfileBinding.tvEmail.text = doc["email"].toString()
                }
            }

        activityProfileBinding.toolbar.setNavigationOnClickListener { onBackPressed() }

        activityProfileBinding.btnLogout.setOnClickListener {
            fAuth.signOut()
            showControl(true)
            Intent(this, SplashScreenActivity::class.java).also {
                it.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        showControl(false)
    }

    private fun showControl(state: Boolean) {
        activityProfileBinding.apply {
            when {
                state -> {
                    progressBar.visibility = View.VISIBLE
                    tvLogout.visibility = View.GONE
                }
                else -> {
                    progressBar.visibility = View.GONE
                    tvLogout.visibility = View.VISIBLE
                }
            }
        }
    }

}