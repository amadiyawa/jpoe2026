plugins {
    id("local.library")
}

android {
    namespace = "com.amadiyawa.droidkotlin.base"
}

dependencies {
    api(libs.kotlin)
    api(libs.androidx.core.ktx)
    api(libs.coroutines)
    api(libs.timber)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.material3)
    api(libs.material)
    api(libs.androidx.material3.android)
    api(libs.material3WindowSize)
    api(libs.androidx.activity.compose)
    api(libs.navigationCompose)
    api(libs.bundles.compose)
    api(platform(libs.koin.bom))
    api(libs.koin.core)
    api(libs.koin.compose)
    api(libs.koin.androidx.compose)
    api(libs.koin.androidx.compose.navigation)
    api(libs.bundles.retrofit)
    api(libs.bundles.room)
    api(libs.datastorePreferences)
    api(libs.systemUiController)
    api(libs.libphonenumber)
    api(libs.bundles.keelo)
    ksp(libs.roomCompiler)
    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.junitJupiterEngine)
}