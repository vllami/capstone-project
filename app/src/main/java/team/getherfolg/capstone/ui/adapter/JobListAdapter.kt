package team.getherfolg.capstone.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import team.getherfolg.capstone.data.job_list.JobListEntity
import team.getherfolg.capstone.databinding.ItemJobListBinding

class JobListAdapter : RecyclerView.Adapter<JobListAdapter.JobListViewHolder>() {
    private val listJob = ArrayList<JobListEntity>()

    fun setListJob(listJob: List<JobListEntity>?) {
        if (listJob == null) return
        this.listJob.clear()
        this.listJob.addAll(listJob)
    }

    inner class JobListViewHolder(private val binding: ItemJobListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(job: JobListEntity) {
            with(binding) {
                tvJobName.text = job.job_name
                tvCompany.text = job.company_name
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JobListAdapter.JobListViewHolder {
        val view = ItemJobListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobListViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobListAdapter.JobListViewHolder, position: Int) {
        holder.bind(job = listJob[position])
    }

    override fun getItemCount(): Int = listJob.size
}