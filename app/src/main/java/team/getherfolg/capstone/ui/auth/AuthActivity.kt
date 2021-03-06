package team.getherfolg.capstone.ui.auth

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import team.getherfolg.capstone.databinding.ActivityAuthBinding
import team.getherfolg.capstone.ui.auth.login.LogInActivity
import team.getherfolg.capstone.ui.auth.onboarding.OnboardingPagerAdapter
import team.getherfolg.capstone.ui.auth.signup.SignUpActivity
import team.getherfolg.capstone.ui.main.MainActivity
import team.getherfolg.capstone.ui.preference.Constant
import team.getherfolg.capstone.ui.preference.PreferenceHelper

class AuthActivity : AppCompatActivity() {

    private lateinit var activityAuthBinding: ActivityAuthBinding
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAuthBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(activityAuthBinding.root)

        preferenceHelper = PreferenceHelper(this)

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

    override fun onStart() {
        super.onStart()
        if (preferenceHelper.getDataBoolean(Constant.PREF_IS_LOGIN)) {
            Intent(this, MainActivity::class.java).also {
                it.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
            finish()
        }
    }
}