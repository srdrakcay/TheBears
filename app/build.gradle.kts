@Suppress("DSL_SCOPE_VIOLATION") plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt)
    alias(libs.plugins.android.navigation.safeargs)

}

android {
    namespace = "com.serdar.thebears"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.serdar.thebears"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.serdar.thebears.profile.TestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            multiDexEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
    sourceSets {
        getByName("debug").res.srcDirs("$rootDir/navigation/src/main/sharedRes")
    }

    testOptions {
        animationsDisabled = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":localization"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:signin"))
    implementation(project(":feature:home"))
    implementation(project(":feature:profile"))

    //AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)

    //Material
    implementation(libs.material)

    //Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Test-Junit
    testImplementation(libs.junit)

    //Test-Junit-Android
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    //Test-Espresso
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.espresso.intents)


    //Test-Mock-Android
    androidTestImplementation(libs.mock)

    //Test-Truth
    testImplementation(libs.truth)

    //Test-Turbine
    testImplementation(libs.turbine)

    //Test-Core
    testImplementation(libs.core.testing)

    //Test-Hilt
    kaptTest(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.testing)
    kaptAndroidTest(libs.hilt.compiler)

    //Test-Fragment
    debugImplementation(libs.fragment.testing)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel)

    // LiveData
    implementation(libs.androidx.lifecycle.livedata)


}