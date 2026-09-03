import com.github.triplet.gradle.androidpublisher.ResolutionStrategy

plugins {
    id("kmp-application")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.gradlePlayPublisher)
}

// Release signing is only wired up when all four env vars are present (i.e. on CI).
// Local/debug builds and `assembleDebug` are unaffected when they're unset.
val releaseKeystorePath = System.getenv("KEYSTORE_PATH")
val releaseKeystorePassword = System.getenv("KEYSTORE_PASSWORD")
val releaseKeyAlias = System.getenv("KEY_ALIAS")
val releaseKeyPassword = System.getenv("KEY_PASSWORD")
val hasReleaseSigning = listOf(releaseKeystorePath, releaseKeystorePassword, releaseKeyAlias, releaseKeyPassword)
    .all { !it.isNullOrBlank() }

android {
    namespace = "by.freiding.braindrop"

    defaultConfig {
        applicationId = "by.freiding.braindrop"
        versionCode = 1
        versionName = "1.0"
    }

    if (hasReleaseSigning) {
        signingConfigs {
            create("release") {
                storeFile = file(releaseKeystorePath!!)
                storePassword = releaseKeystorePassword
                keyAlias = releaseKeyAlias
                keyPassword = releaseKeyPassword
            }
        }
        buildTypes {
            getByName("release") {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
}

// Track defaults to "internal"; CI overrides it per branch via -PplayTrack=...
// versionCode is auto-resolved against Play Console's current max (across all
// tracks) at publish time, so staging and main can never collide even though
// they're separate Jenkins multibranch jobs with independent BUILD_NUMBERs.
play {
    serviceAccountCredentials.set(file(System.getenv("PLAY_SERVICE_ACCOUNT_JSON") ?: "play-service-account.json"))
    track.set(providers.gradleProperty("playTrack").getOrElse("internal"))
    defaultToAppBundles.set(true)
    resolutionStrategy.set(ResolutionStrategy.AUTO)
}

dependencies {
    implementation(projects.shared)
    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.android)
    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)
}
