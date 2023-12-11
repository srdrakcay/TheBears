package com.serdar.thebears

import android.app.Application
import com.serdar.common.applicationlistener.ApplicationLifecycleHandler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheBearsApp:Application() {
    override fun onCreate() {
        super.onCreate()
        val handler = ApplicationLifecycleHandler()
        registerActivityLifecycleCallbacks(handler)
        registerComponentCallbacks(handler)
    }
}