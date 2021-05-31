package team.getherfolg.capstone.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import team.getherfolg.capstone.databinding.ActivityAuthenticationBinding
import team.getherfolg.capstone.ui.authentication.onboarding.OnboardingPagerAdapter
import team.getherfolg.capstone.ui.main.MainActivity
import team.getherfolg.capstone.ui.authentication.login.LogInActivity
import team.getherfolg.capstone.ui.authentication.signup.SignUpActivity

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)


        setPager()
        homeBinding.apply {
            btnSignUp.setOnClickListener {
                Intent(this@AuthenticationActivity, SignUpActivity::class.java).also {
                    startActivity(it)
                }
            }
            btnLogin.setOnClickListener {
                Intent(this@AuthenticationActivity, LogInActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun setPager() {
        homeBinding.apply {
            val pagerOnBoard = OnboardingPagerAdapter(supportFragmentManager)
            viewPager.adapter = pagerOnBoard

            tabsIndicator.setupWithViewPager(viewPager)
        }
    }
}