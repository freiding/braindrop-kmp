plugins {
    id("kmp-feature")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

compose {
    resources {
        packageOfResClass = "by.freiding.braindrop.feature.irregularverbs"
    }
}

kotlin {
    androidLibrary {
        namespace = "by.freiding.braindrop.feature.irregularverbs"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreUi)
            implementation(projects.core.coreNavigation)
            implementation(projects.core.coreDatabase)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.navigation.compose)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.compose.components.resources)
        }
    }
}
