plugins {
    id("local.library")
}

android {
    namespace = "com.amadiyawa.onboarding"
}

dependencies {
    api(projects.featureBase)

    testImplementation(libs.bundles.test)

    testRuntimeOnly(libs.junitJupiterEngine)
}