package com.reddit.rickandmortyapp

import android.app.Application
import com.reddit.rickandmortyapp.common.network.NetworkModule
import com.reddit.rickandmortyapp.main.MainModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class RedditApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupKoin()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@RedditApplication)
            modules(
                listOf(
                    NetworkModule.create(),
                    MainModule.create(),
                )
            )
        }
    }
}