package team.getherfolg.capstone.ui.register.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import team.getherfolg.capstone.databinding.ActivityAuthenticationBinding
import team.getherfolg.capstone.ui.main.MainActivity
import team.getherfolg.capstone.ui.register.onboard.OnBoardPagerAdapter

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
                Intent(this@AuthenticationActivity, LoginActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun setPager() {
        homeBinding.apply {
            val pagerOnBoard = OnBoardPagerAdapter(supportFragmentManager)
            viewPager.adapter = pagerOnBoard

            tabsIndicator.setupWithViewPager(viewPager)
            OverScrollDecoratorHelper.setUpOverScroll(viewPager)
        }
    }
}