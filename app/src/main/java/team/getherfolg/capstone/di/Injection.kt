package team.getherfolg.capstone.di

import android.content.Context
import team.getherfolg.capstone.data.repository.JobsRepository
import team.getherfolg.capstone.data.source.RemoteDataSource

object Injection {

    fun provideRepository(context: Context): JobsRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return JobsRepository.getInstance(remoteDataSource)
    }

}