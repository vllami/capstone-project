package team.getherfolg.capstone.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.data.form.LoginUserResponse
import team.getherfolg.capstone.databinding.FragmentProfileBinding
import team.getherfolg.capstone.networking.SuitableClient
import team.getherfolg.capstone.ui.auth.AuthActivity
import team.getherfolg.capstone.ui.preference.PreferenceHelper

class ProfileFragment : Fragment() {

    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var prefHelper: PreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prefHelper = PreferenceHelper(requireContext())

        profileBinding.btnLogout.setOnClickListener {
            SuitableClient.getService().logoutUser().enqueue(object : Callback<LoginUserResponse> {
                override fun onResponse(
                    call: Call<LoginUserResponse>,
                    response: Response<LoginUserResponse>
                ) {
                    prefHelper.clear()
                    showControl(true)
                    Intent(activity, AuthActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(it)
                    }
                }

                override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
                    showControl(false)
                }

            })

        }
    }

    private fun showControl(state: Boolean) {
        profileBinding.apply {
            when {
                state -> {
                    progressBar.visibility = View.VISIBLE
                    tvLogout.visibility = View.GONE
                }
                else -> {
                    progressBar.visibility = View.GONE
                    tvLogout.visibility = View.VISIBLE
                }
            }
        }
    }

}