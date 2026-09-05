package by.freiding.braindrop.core.ui.di

import by.freiding.braindrop.core.ui.tts.NoOpTextToSpeechPlayer
import by.freiding.braindrop.core.ui.tts.TextToSpeechPlayer
import org.koin.dsl.module

/**
 * iOS bindings for core-ui. Speech synthesis is not wired on iOS yet, so the no-op player is used.
 * (The iOS framework link is currently broken project-wide — see the repo memory — so this is not
 * build-verified.)
 */
val coreUiIosModule = module {
    single<TextToSpeechPlayer> { NoOpTextToSpeechPlayer }
}
