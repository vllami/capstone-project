package team.getherfolg.capstone.ui.main.article

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.getInstance
import team.getherfolg.capstone.R
import team.getherfolg.capstone.databinding.FragmentArticleBinding.inflate as FragmentArticleBinding
import team.getherfolg.capstone.ui.authentication.AuthenticationActivity

class ArticleFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        FragmentArticleBinding(layoutInflater, container, false).also {
            return it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = getInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> {
                firebaseAuth.signOut()

                Intent(activity, AuthenticationActivity::class.java).also {
                    it.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}