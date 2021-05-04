package team.getherfolg.capstone.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import team.getherfolg.capstone.R
import team.getherfolg.capstone.ui.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {

    private val delay: Long = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        Handler(mainLooper).postDelayed({
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
            }
        }, delay)
    }
}