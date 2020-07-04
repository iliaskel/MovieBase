package com.example.moviebase

import android.app.Application
import com.example.moviebase.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class MovieBaseApplication() : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MovieBaseApplication)
            fragmentFactory()
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    repositoryModule,
                    mainViewModel,
                    detailedMovieViewModel
                )
            )
        }
    }
}