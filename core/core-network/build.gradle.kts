plugins {
    id("kmp-library")
}

kotlin {
    androidLibrary {
        namespace = "by.freiding.braindrop.core.network"
    }

    // Ktor will be added here when server sync is introduced
}
