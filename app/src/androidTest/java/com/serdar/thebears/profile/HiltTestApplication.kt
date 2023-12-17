package com.serdar.thebears.profile

import android.app.Application
import dagger.hilt.android.testing.CustomTestApplication

@CustomTestApplication(Application::class)
interface HiltTestApplication {
}