@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)

}


android {
    namespace = "com.serdar.profile"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "com.serdar.profile.TestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":navigation"))
    implementation(project(":socket"))
    implementation(project(":localization"))

    //AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)

    //Material
    implementation(libs.material)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation(libs.firebase.auth.ktx)

    //Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.coroutines.testing)

    //Test-Mock
    testImplementation(libs.mock)
    testImplementation(libs.mockk.mockk)
    testImplementation(libs.mockk.android)
    androidTestImplementation(libs.mock.android)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
    testImplementation(libs.core.testing)
    androidTestImplementation(libs.hilt.testing)
    androidTestImplementation(libs.fragment.testing)
    kaptTest(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel)

    // LiveData
    implementation(libs.androidx.lifecycle.livedata)


    //Navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    //Retrofit
    implementation(libs.retrofit)
    implementation (libs.retrofit.gson)
    implementation (libs.retrofit.moshi)


    //Okhttp
    implementation(libs.okhttp)

    //Gson
    implementation(libs.gson)

    //Square OkHttp-Retrofit-Moshi
    implementation(libs.okhttp)

}