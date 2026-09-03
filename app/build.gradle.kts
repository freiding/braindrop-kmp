import com.github.triplet.gradle.androidpublisher.ResolutionStrategy

plugins {
    id("kmp-application")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.gradlePlayPublisher)
}

// Release signing is only wired up when all four env vars are present (i.e. on CI).
// Local/debug builds and `assembleDebug` are unaffected when they're unset.
val releaseKeystorePath = providers.environmentVariable("KEYSTORE_PATH").orNull
val releaseKeystorePassword = providers.environmentVariable("KEYSTORE_PASSWORD").orNull
val releaseKeyAlias = providers.environmentVariable("KEY_ALIAS").orNull
val releaseKeyPassword = providers.environmentVariable("KEY_PASSWORD").orNull
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
    serviceAccountCredentials.set(
        file(providers.environmentVariable("PLAY_SERVICE_ACCOUNT_JSON").getOrElse("play-service-account.json")),
    )
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
