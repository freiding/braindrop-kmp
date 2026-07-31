plugins {
    id("kmp-library")
}

kotlin {
    androidLibrary {
        namespace = "by.freiding.braindrop.core.common"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}
