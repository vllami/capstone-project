package team.getherfolg.capstone.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import team.getherfolg.capstone.databinding.FragmentThirdScreenBinding

class ThirdScreenFragment : Fragment() {

    private lateinit var binding: FragmentThirdScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentThirdScreenBinding.inflate(layoutInflater, container, false)
        return view.root
    }
}