package team.getherfolg.capstone.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import team.getherfolg.capstone.ui.screen.FirstScreenFragment
import team.getherfolg.capstone.ui.screen.SecondScreenFragment
import team.getherfolg.capstone.ui.screen.ThirdScreenFragment

class OnBoardPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment =
        when(position) {
            0 -> FirstScreenFragment()
            1 -> SecondScreenFragment()
            2 -> ThirdScreenFragment()
            else -> Fragment()
        }
}