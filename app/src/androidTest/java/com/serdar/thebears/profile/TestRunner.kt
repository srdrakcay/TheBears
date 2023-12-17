package com.serdar.thebears.profile

import android.app.Application
import android.content.Context
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.runner.AndroidJUnitRunner
import com.serdar.thebears.HiltTestActivity
import dagger.hilt.android.testing.HiltTestApplication

class TestRunner: AndroidJUnitRunner(){
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}