plugins {
    id("kmp-library")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidLibrary {
        namespace = "by.freiding.braindrop.core.navigation"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.core)
        }
    }
}
