package team.getherfolg.capstone.ui.authentication.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import team.getherfolg.capstone.databinding.FragmentOnboarding1Binding

class Onboarding1Fragment : Fragment() {

    private lateinit var binding: FragmentOnboarding1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboarding1Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

}