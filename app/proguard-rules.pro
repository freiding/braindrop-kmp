# R8 / ProGuard rules for the :app release build.
#
# Most dependencies here (Compose Multiplatform, kotlinx coroutines/serialization/
# datetime, Koin, SQLDelight, Firebase) ship their own consumer rules, so this
# file only carries app-specific keeps.

# Keep enough debug info for Play Console / Crashlytics to de-obfuscate stack
# traces from the uploaded mapping file, without leaking full source paths.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ---------------------------------------------------------------------------
# kotlinx.serialization
# ---------------------------------------------------------------------------
# Type-safe Navigation routes (core-navigation/Routes.kt) are @Serializable and
# resolved through serializer() lookups that R8 cannot trace. These are the
# rules recommended by the kotlinx.serialization docs; they also cover any
# @Serializable DTOs added later.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# The route serial name (a class's fully qualified name) doubles as the analytics
# screen name — App.kt derives it from NavDestination.route. Keep the route
# hierarchy un-obfuscated so those names stay stable and human-readable.
-keep class by.freiding.braindrop.core.navigation.Routes { *; }
-keep class by.freiding.braindrop.core.navigation.Routes$* { *; }
