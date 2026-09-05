package by.freiding.braindrop.core.ui.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

/**
 * [TextToSpeechPlayer] backed by the platform [TextToSpeech] engine. Initialisation is async; calls
 * to [speak] made before the engine is ready are dropped rather than queued.
 */
class AndroidTextToSpeechPlayer(
    context: Context,
) : TextToSpeechPlayer {
    private var ready = false

    private val engine: TextToSpeech = TextToSpeech(context.applicationContext, ::onInit)

    private fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            engine.setLanguage(Locale.ENGLISH)
            ready = true
        }
    }

    override fun speak(text: String) {
        if (!ready || text.isBlank()) return
        engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, text.hashCode().toString())
    }

    override fun stop() {
        if (ready) engine.stop()
    }
}
