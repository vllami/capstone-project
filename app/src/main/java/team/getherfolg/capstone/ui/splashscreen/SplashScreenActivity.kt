package team.getherfolg.capstone.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import team.getherfolg.capstone.R.layout
import team.getherfolg.capstone.ui.auth.AuthActivity

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_SCREEN_DELAY = 2_500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_splashscreen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }, SPLASH_SCREEN_DELAY.toLong())
    }

}