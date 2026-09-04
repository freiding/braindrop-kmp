import com.github.triplet.gradle.androidpublisher.ResolutionStrategy

plugins {
    id("kmp-application")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.gradlePlayPublisher)
}

// Firebase (Analytics + Crashlytics) is wired only when app/google-services.json is present.
// Contributor and CI builds without the file still compile and run — analytics falls back to
// the no-op implementations (see BrainDropApplication). Drop the real file in from the
// Firebase console to enable reporting; it is safe to commit (client config, shipped in the APK).
// Plugin ids are hardcoded here (not via libs.plugins.*) because they're only applied
// conditionally; their versions still come from the catalog through the root build's
// `apply false` declarations.
if (file("google-services.json").exists()) {
    apply(plugin = "com.google.gms.google-services")
    apply(plugin = "com.google.firebase.crashlytics")
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
        versionName = "1.0.0"
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
    implementation(projects.core.coreAnalytics)
    implementation(projects.core.coreAnalyticsFirebase)
    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.android)
    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)
}
