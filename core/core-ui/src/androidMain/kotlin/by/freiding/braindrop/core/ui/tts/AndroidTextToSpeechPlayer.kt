package by.freiding.braindrop.core.ui.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

/**
 * [TextToSpeechPlayer] backed by the platform [TextToSpeech] engine — a process-lifetime singleton.
 * Initialisation is async; calls to [speak] made before the engine reports ready are dropped rather
 * than queued.
 */
class AndroidTextToSpeechPlayer(
    context: Context,
) : TextToSpeechPlayer {
    private var ready = false

    // Nullable + assigned in init (not a self-referencing property initializer) so the
    // OnInitListener can read it safely even if some OEM invokes the listener synchronously.
    private var engine: TextToSpeech? = null

    init {
        engine = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                engine?.setLanguage(Locale.ENGLISH)
                ready = true
            }
        }
    }

    override fun speak(text: String) {
        if (!ready || text.isBlank()) return
        engine?.speak(text, TextToSpeech.QUEUE_FLUSH, null, text.hashCode().toString())
    }

    override fun stop() {
        engine?.stop()
    }
}
