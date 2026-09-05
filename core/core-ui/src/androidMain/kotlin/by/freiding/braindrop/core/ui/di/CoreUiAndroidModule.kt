package by.freiding.braindrop.core.ui.di

import by.freiding.braindrop.core.ui.tts.AndroidTextToSpeechPlayer
import by.freiding.braindrop.core.ui.tts.TextToSpeechPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/** Android bindings for core-ui. Registered from the app's `startKoin` block. */
val coreUiAndroidModule = module {
    single<TextToSpeechPlayer> { AndroidTextToSpeechPlayer(androidContext()) }
}
