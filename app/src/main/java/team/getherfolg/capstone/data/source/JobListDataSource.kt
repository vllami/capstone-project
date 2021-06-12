package team.getherfolg.capstone.data.source

import androidx.lifecycle.LiveData
import team.getherfolg.capstone.data.entity.JobsEntity

interface JobListDataSource {

    fun loadListJob(): LiveData<List<JobsEntity>>

}