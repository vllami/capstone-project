package team.getherfolg.capstone.ui.main.home.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import team.getherfolg.capstone.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var activityProfileBinding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(activityProfileBinding.root)
    }

}