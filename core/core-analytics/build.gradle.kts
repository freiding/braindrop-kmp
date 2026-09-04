plugins {
    id("kmp-library")
}

kotlin {
    androidLibrary {
        namespace = "by.freiding.braindrop.core.analytics"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}
