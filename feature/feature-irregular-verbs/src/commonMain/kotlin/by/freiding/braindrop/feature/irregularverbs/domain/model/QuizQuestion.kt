package by.freiding.braindrop.feature.irregularverbs.domain.model

data class QuizQuestion(
    val verb: IrregularVerb,
    val type: QuizType,
    val questionText: String,
    val correctAnswer: String,
    val options: List<String>,
)
