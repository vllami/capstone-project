package team.getherfolg.capstone.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import team.getherfolg.capstone.R
import team.getherfolg.capstone.data.entity.JobsEntity
import team.getherfolg.capstone.databinding.ItemJobsBinding

class JobsAdapter : RecyclerView.Adapter<JobsAdapter.JobsViewHolder>() {

    private lateinit var jobsBinding: ItemJobsBinding

    private val listJob = ArrayList<JobsEntity>()

    fun setJobs(listJob: List<JobsEntity>?) {
        if (listJob != null) {
            this.listJob.clear()
            this.listJob.addAll(listJob)
        }
    }

    inner class JobsViewHolder(private val binding: ItemJobsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(job: JobsEntity) {
            with(binding) {
                tvCompanyName.text = job.companyName
                tvCategoryName.text = job.categoryName
                tvJobName.text = job.jobName
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JobsAdapter.JobsViewHolder {
        val view = ItemJobsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobsViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobsAdapter.JobsViewHolder, position: Int) {
        val binding = ItemJobsBinding.bind(holder.itemView)
        with(binding) {
            cvItemCourse.apply {
                if (listJob.size == 1) {
                    setBackgroundResource(R.drawable.ripple_radius_bg_white)
                } else {
                    when (position) {
                        0 -> setBackgroundResource(R.drawable.ripple_top_bg_white)
                        listJob.size - 1 -> setBackgroundResource(R.drawable.ripple_bottom_bg_white)
                        else -> setBackgroundResource(R.drawable.ripple_bg_white)
                    }
                }
            }
        }

        holder.bind(job = listJob[position])
    }

    override fun getItemCount(): Int = listJob.size
}