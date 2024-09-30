package com.example.smarttask

import android.app.Application
import com.example.smarttask.di.AppModule
import com.example.smarttask.di.NetworkModule
import com.example.smarttask.di.featureModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(AppModule + NetworkModule + featureModules())
        }
    }
}
