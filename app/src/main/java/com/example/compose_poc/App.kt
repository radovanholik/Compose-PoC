package com.example.compose_poc

import android.app.Application
import androidx.compose.animation.ExperimentalAnimationApi
import com.example.compose_poc.di.appModule
import com.example.compose_poc.di.viewModelModule
import com.google.accompanist.pager.ExperimentalPagerApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

@ExperimentalAnimationApi
@ExperimentalPagerApi
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    private fun initKoin() {
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(
                // TODO - add modules
                appModule,
                viewModelModule
            )
        }
    }
}