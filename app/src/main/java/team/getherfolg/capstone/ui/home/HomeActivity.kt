package team.getherfolg.capstone.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import team.getherfolg.capstone.databinding.ActivityHomeBinding
import team.getherfolg.capstone.ui.adapter.OnBoardPagerAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        setPager()
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