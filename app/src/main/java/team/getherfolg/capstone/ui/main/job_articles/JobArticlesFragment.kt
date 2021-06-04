package team.getherfolg.capstone.ui.main.job_articles

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import team.getherfolg.capstone.R
import team.getherfolg.capstone.ui.auth.AuthActivity
import team.getherfolg.capstone.databinding.FragmentJobArticlesBinding.inflate as FragmentJobArticlesBinding

class JobArticlesFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        FragmentJobArticlesBinding(layoutInflater, container, false).also {
            return it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> {

                Intent(activity, AuthActivity::class.java).also {
                    it.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}