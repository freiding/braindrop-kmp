plugins {
    id("com.android.application")
}

android {
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        targetSdk = 36
    }

    buildTypes {
        getByName("release") {
            // R8 code shrinking/obfuscation is on for release builds. The deobfuscation
            // mapping it generates is embedded by AGP into the .aab at
            // BUNDLE-METADATA/com.android.tools.build.obfuscation/proguard.map, so
            // `:app:publishReleaseBundle` uploads it to Play Console together with the
            // bundle — no extra Gradle Play Publisher config is needed for app bundles.
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
