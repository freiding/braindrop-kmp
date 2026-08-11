package by.freiding.braindrop.feature.tenses.domain.model

data class TenseComparison(
    val id: String,
    val tenseIdA: String,
    val tenseIdB: String,
    val tip: String,
    val pointsA: List<String>,
    val pointsB: List<String>,
    val exampleA: TenseExample,
    val exampleB: TenseExample,
)
