package team.getherfolg.capstone.ui.main.profile

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import team.getherfolg.capstone.R
import team.getherfolg.capstone.databinding.FragmentProfileBinding
import team.getherfolg.capstone.ui.register.auth.AuthenticationActivity

class ProfileFragment : Fragment() {

    private lateinit var fragmentProfileBinding: FragmentProfileBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        fragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return fragmentProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> {
                mAuth.signOut()
                val intent = Intent(activity, AuthenticationActivity::class.java)
                intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}