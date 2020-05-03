package com.example.moviebase.model.di

import androidx.room.Room
import com.example.moviebase.model.database.TMDBDB
import com.example.moviebase.model.network.api.TMDBApiService
import com.example.moviebase.model.repository.RepositoryImpl
import com.example.moviebase.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// region modules

val networkModule = module {
    single { provideRetrofit() }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), TMDBDB::class.java, "tmdb_db")
    }
}

val repositoryModule = module {
    single {
        RepositoryImpl(tmdbDb = get(), tmdbApiService = get())
    }
}

// end region

// region Private Functions

private fun provideRetrofit(): TMDBApiService {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(getHttpClientInterceptor())
        .build()
        .create(TMDBApiService::class.java)
}

private fun getHttpClientInterceptor(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    val httpClient = OkHttpClient.Builder()

    return httpClient.addInterceptor(logging).build()
}

// endregion