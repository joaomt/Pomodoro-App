plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    kotlin("android")
}

android {

    compileSdk = PomodoroVersions.compileSdk
    defaultConfig {
        applicationId = "com.superplayer.pomodoro"
        minSdk = PomodoroVersions.minSdk
        targetSdk = PomodoroVersions.targetSdk
        versionCode = PomodoroVersions.versionCode
        versionName = PomodoroVersions.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = PomodoroVersions.javaCompileVersion
        targetCompatibility = PomodoroVersions.javaCompileVersion
    }

    packagingOptions {
        resources.excludes.apply {
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }

    dataBinding {
        enable = true
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.hilt.android)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.lottie)
    implementation(libs.circularprogressbar)

    kapt(libs.androidx.lifecycle.compiler)
    kapt(libs.hilt.compiler)
    kapt(libs.androidx.room.compiler)
}
