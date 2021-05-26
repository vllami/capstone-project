package team.getherfolg.capstone.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import team.getherfolg.capstone.R
import team.getherfolg.capstone.R.id
import team.getherfolg.capstone.databinding.ActivityMainBinding
import team.getherfolg.capstone.databinding.ActivityMainBinding.inflate

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = inflate(layoutInflater)
        activityMainBinding.apply {
            setContentView(root)
            bottomNavigation.setupWithNavController(
                findNavController(id.nav_host_fragment)
            )
        }
    }
}