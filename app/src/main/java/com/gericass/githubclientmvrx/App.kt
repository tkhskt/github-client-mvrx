package com.gericass.githubclientmvrx

import android.app.Application
import com.facebook.stetho.Stetho
import com.gericass.githubclientmvrx.di.Modules.apiModule
import com.gericass.githubclientmvrx.di.Modules.navigationModule
import com.gericass.githubclientmvrx.di.Modules.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Android context
            androidContext(this@App)
            // modules
            modules(apiModule, repositoryModule, navigationModule)
        }
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            Timber.plant(Timber.DebugTree())
        }
    }
}