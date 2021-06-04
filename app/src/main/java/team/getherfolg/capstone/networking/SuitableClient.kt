package team.getherfolg.capstone.networking

import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.converter.gson.GsonConverterFactory.create
import okhttp3.OkHttpClient.Builder as OkHttpClient
import retrofit2.Retrofit.Builder as Retrofit

class SuitableClient {

    companion object {
        fun getService(): SuitableService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
                .setLevel(Level.BODY)
            val okHttpClient = OkHttpClient()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            Retrofit()
                .baseUrl("https://jobrec-ofah4hdyfa-et.a.run.app")
                .addConverterFactory(create())
                .client(okHttpClient)
                .build().also {
                    return it.create(SuitableService::class.java)
                }
        }
    }

}