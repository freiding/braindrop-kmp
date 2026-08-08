plugins {
    id("kmp-library")
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidLibrary {
        namespace = "by.freiding.braindrop.core.database"
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
        }
        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
        commonMain.dependencies {
            implementation(projects.core.coreCommon)
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("by.freiding.braindrop.database")
        }
    }
}
