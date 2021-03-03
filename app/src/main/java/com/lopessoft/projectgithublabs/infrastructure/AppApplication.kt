package com.lopessoft.projectgithublabs.infrastructure

import android.app.Application
import com.lopessoft.projectgithublabs.infrastructure.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AppApplication)
            modules(AppModule.module)
        }
    }
}