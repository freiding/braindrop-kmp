package by.freiding.braindrop.core.ui.tts

/**
 * Speaks short English strings aloud — the speaker buttons on the Vocabulary screens.
 *
 * Provided via Koin: a real Android implementation on device, a no-op elsewhere. Screens obtain it
 * with `koinInject<TextToSpeechPlayer>()` so it never touches ViewModels or UI state.
 */
interface TextToSpeechPlayer {
    fun speak(text: String)

    fun stop()
}

/** Fallback used where platform speech synthesis isn't wired up (tests, iOS). */
object NoOpTextToSpeechPlayer : TextToSpeechPlayer {
    override fun speak(text: String) = Unit

    override fun stop() = Unit
}
