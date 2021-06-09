package team.getherfolg.capstone.ui.main.jobs

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import team.getherfolg.capstone.R
import team.getherfolg.capstone.databinding.FragmentJobsBinding
import team.getherfolg.capstone.ui.adapter.JobsAdapter
import team.getherfolg.capstone.ui.auth.AuthActivity
import team.getherfolg.capstone.ui.viewmodel.JobsViewModel
import team.getherfolg.capstone.ui.viewmodel.ViewModelFactory
import team.getherfolg.capstone.databinding.FragmentJobsBinding.inflate as FragmentJobsBinding

class JobsFragment : Fragment() {

    private lateinit var jobsAdapter: JobsAdapter
    private lateinit var fragmentJobsBinding: FragmentJobsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        fragmentJobsBinding = FragmentJobsBinding(layoutInflater, container, false)
        return fragmentJobsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val jobViewModel = ViewModelProvider(this, factory)[JobsViewModel::class.java]
            jobsAdapter = JobsAdapter()

            jobViewModel.getJobs().observe(viewLifecycleOwner, {
                jobsAdapter.apply {
                    setJobs(it)
                    notifyDataSetChanged()
                }
                with(fragmentJobsBinding.rvJobList) {
                    layoutManager = LinearLayoutManager(activity)
                    setHasFixedSize(true)
                    adapter = jobsAdapter
                }
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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