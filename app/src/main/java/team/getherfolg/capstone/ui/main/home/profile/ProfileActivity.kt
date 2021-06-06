package team.getherfolg.capstone.ui.main.home.profile

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.data.form.LoginUserResponse
import team.getherfolg.capstone.databinding.ActivityProfileBinding
import team.getherfolg.capstone.networking.SuitableClient
import team.getherfolg.capstone.ui.auth.AuthActivity
import team.getherfolg.capstone.ui.preference.PreferenceHelper

class ProfileActivity : AppCompatActivity() {

    private lateinit var activityProfileBinding: ActivityProfileBinding
    private lateinit var prefHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(activityProfileBinding.root)

        prefHelper = PreferenceHelper(this)

        activityProfileBinding.toolbar.setNavigationOnClickListener { onBackPressed() }

        activityProfileBinding.btnLogout.setOnClickListener {
            SuitableClient.getService().logoutUser().enqueue(object : Callback<LoginUserResponse> {
                override fun onResponse(
                    call: Call<LoginUserResponse>,
                    response: Response<LoginUserResponse>
                ) {
                    prefHelper.clear()
                    showControl(true)
                    Intent(this@ProfileActivity, AuthActivity::class.java).also {
                        it.flags = FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK
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
        activityProfileBinding.apply {
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