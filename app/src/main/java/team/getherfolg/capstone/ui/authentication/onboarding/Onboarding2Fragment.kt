package team.getherfolg.capstone.ui.authentication.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import team.getherfolg.capstone.databinding.FragmentOnboarding2Binding

class Onboarding2Fragment : Fragment() {

    private lateinit var binding: FragmentOnboarding2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboarding2Binding.inflate(layoutInflater, container, false)
        return binding.root
    }
}