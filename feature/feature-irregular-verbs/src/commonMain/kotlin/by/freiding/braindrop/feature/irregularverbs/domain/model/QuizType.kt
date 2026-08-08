package by.freiding.braindrop.feature.irregularverbs.domain.model

enum class QuizType {
    EN_TO_RU,
    RU_TO_EN,
    VERB_FORMS,
    ;

    companion object {
        fun fromString(value: String): QuizType = entries.firstOrNull { it.name == value } ?: EN_TO_RU
    }
}
