package com.ruizurraca.techtest

import android.app.Application
import androidx.multidex.MultiDex
import com.ruizurraca.techtest.di.AppModules
import com.ruizurraca.techtest.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApp : Application() {

    private val TAG = MainApp::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(listOf(AppModules, NetworkModule))
        }

    }
}