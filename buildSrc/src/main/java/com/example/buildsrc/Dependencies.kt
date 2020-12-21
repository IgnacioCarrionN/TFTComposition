package com.example.buildsrc

object Versions {
    const val androidXCore = "1.3.2"
    const val appCompat = "1.2.0"
    const val material = "1.2.1"
    const val compose = "1.0.0-alpha09"
    const val composeNavigation = "1.0.0-alpha04"
    const val jUnit = "4.+"
    const val runtimeLifecycle = "2.3.0-rc01"
    const val jUnitExt = "1.1.2"
    const val espresso = "3.3.0"
    const val room = "2.2.6"
    const val retrofit = "2.9.0"
    const val coroutines = "1.4.2"
    const val hilt = "2.28-alpha"
    const val mockWebServer = "4.9.0"
    const val mockk = "1.10.3-jdk8"
}

object KotlinDependencies {
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
}

object AndroidDependencies {
    const val androidXCore = "androidx.core:core-ktx:${Versions.androidXCore}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val composeUI = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composeTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
    const val runtimeLifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.runtimeLifecycle}"


    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

}

object TestDependencies {
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val roomTesting = "androidx.room:room-testing:${Versions.room}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
}

object AndroidTestDependencies {
    const val jUnitExt = "androidx.test.ext:junit:${Versions.jUnitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}