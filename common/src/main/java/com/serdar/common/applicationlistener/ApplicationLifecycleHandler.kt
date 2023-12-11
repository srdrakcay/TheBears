package com.serdar.common.applicationlistener

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log


class ApplicationLifecycleHandler : ActivityLifecycleCallbacks,
    ComponentCallbacks2 {
    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {

    }
    override fun onActivityStarted(activity: Activity) {
        Log.d(TAG, "onActivityCreated")

    }
    override fun onActivityResumed(activity: Activity) {
        if (isInBackground) {
            Log.d(TAG, "app went to foreground")
            isInBackground = false
        }
    }

    override fun onActivityPaused(activity: Activity) {
        Log.d(TAG, "onActivityPaused")

    }
    override fun onActivityStopped(activity: Activity) {
        Log.d(TAG, "onActivityStopped")

    }
    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {
        Log.d(TAG, "onActivityDestroyed")

    }
    override fun onConfigurationChanged(p0: Configuration) {
        Log.d(TAG, "onConfigurationChanged")

    }
    override fun onLowMemory() {}
    override fun onTrimMemory(i: Int) {
        if (i == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Log.d(TAG, "app went to background")
            isInBackground = true
        }
    }

    companion object {
            private val TAG = "TheBearsApps"
        private var isInBackground = false
    }
}