plugins {
    id("kmp-library")
}

kotlin {
    androidLibrary {
        namespace = "by.freiding.braindrop.core.analytics.firebase"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.coreAnalytics)
        }
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.crashlytics)
            implementation(libs.koin.core)
            implementation(libs.koin.android)
        }
    }
}
