plugins {
    id("kmp-application")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "by.freiding.braindrop"

    defaultConfig {
        applicationId = "by.freiding.braindrop"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.android)
    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)
}
