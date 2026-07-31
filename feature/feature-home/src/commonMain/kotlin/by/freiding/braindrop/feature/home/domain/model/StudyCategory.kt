package by.freiding.braindrop.feature.home.domain.model

data class StudyCategory(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val totalItems: Int,
    val studiedCount: Int = 0,
)
