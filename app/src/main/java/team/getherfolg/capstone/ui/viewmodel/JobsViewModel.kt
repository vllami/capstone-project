package team.getherfolg.capstone.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import team.getherfolg.capstone.data.entity.JobsEntity
import team.getherfolg.capstone.data.repository.JobsRepository

class JobsViewModel(private val jobsRepository: JobsRepository) : ViewModel() {
    fun getJobs(): LiveData<List<JobsEntity>> = jobsRepository.loadListJob()
}