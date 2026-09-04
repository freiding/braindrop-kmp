import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
            // All core/feature modules are api-deps, so they are included in this framework
            export(projects.core.coreCommon)
            export(projects.core.coreUi)
            export(projects.core.coreDatabase)
            export(projects.core.coreNavigation)
            export(projects.core.coreAnalytics)
            export(projects.feature.featureHome)
            export(projects.feature.featureProfile)
            export(projects.feature.featureIrregularVerbs)
            export(projects.feature.featureTenses)
        }
    }

    androidLibrary {
        namespace = "by.freiding.braindrop.shared"
        compileSdk = libs.versions.android.compileSdk
            .get()
            .toInt()
        minSdk = libs.versions.android.minSdk
            .get()
            .toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.coreCommon)
            api(projects.core.coreUi)
            api(projects.core.coreDatabase)
            api(projects.core.coreNavigation)
            api(projects.core.coreAnalytics)
            api(projects.feature.featureHome)
            api(projects.feature.featureProfile)
            api(projects.feature.featureIrregularVerbs)
            api(projects.feature.featureTenses)
            implementation(libs.compose.runtime)
            implementation(libs.compose.material3)
            implementation(libs.navigation.compose)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }
        iosMain.dependencies {
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}
