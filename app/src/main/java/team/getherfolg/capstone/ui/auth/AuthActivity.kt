package team.getherfolg.capstone.ui.auth

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import team.getherfolg.capstone.databinding.ActivityAuthBinding
import team.getherfolg.capstone.ui.auth.login.LogInActivity
import team.getherfolg.capstone.ui.auth.onboarding.OnboardingPagerAdapter
import team.getherfolg.capstone.ui.auth.signup.SignUpActivity


class AuthActivity : AppCompatActivity() {

    private lateinit var activityAuthBinding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAuthBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(activityAuthBinding.root)

        setPager()
        activityAuthBinding.apply {
            btnSignUp.setOnClickListener {
                Intent(this@AuthActivity, SignUpActivity::class.java).also {
                    startActivity(it)
                }
            }
            btnLogin.setOnClickListener {
                Intent(this@AuthActivity, LogInActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun setPager() {
        activityAuthBinding.apply {
            val pagerOnBoard = OnboardingPagerAdapter(supportFragmentManager)
            viewPager.adapter = pagerOnBoard

            tabsIndicator.setupWithViewPager(viewPager, true)
        }
    }
}