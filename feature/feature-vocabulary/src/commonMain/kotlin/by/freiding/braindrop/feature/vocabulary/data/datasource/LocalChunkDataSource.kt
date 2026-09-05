package by.freiding.braindrop.feature.vocabulary.data.datasource

import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.DAILY_LIFE_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.DAILY_LIFE_CHUNKS_EXTRA
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.EDUCATION_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.EDUCATION_CHUNKS_EXTRA
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.FEELINGS_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.FEELINGS_CHUNKS_EXTRA
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.HEALTH_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.HEALTH_CHUNKS_EXTRA
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.MONEY_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.MONEY_CHUNKS_EXTRA
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.SOCIAL_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.SOCIAL_CHUNKS_EXTRA
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.TRAVEL_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.TRAVEL_CHUNKS_EXTRA
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.WORK_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.WORK_CHUNKS_EXTRA
import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme

/**
 * The hand-authored chunk catalogue — the Vocabulary section is fully offline, so there is no
 * remote source or DTO layer (same as `LocalPhrasalVerbDataSource`). Content lives in per-theme
 * files under `datasource/seed/`.
 */
class LocalChunkDataSource {
    fun getChunks(): List<Chunk> = CHUNKS

    fun getById(id: String): Chunk? = CHUNKS_BY_ID[id]

    private companion object {
        val CHUNKS: List<Chunk> = (
            WORK_CHUNKS + WORK_CHUNKS_EXTRA +
                TRAVEL_CHUNKS + TRAVEL_CHUNKS_EXTRA +
                MONEY_CHUNKS + MONEY_CHUNKS_EXTRA +
                FEELINGS_CHUNKS + FEELINGS_CHUNKS_EXTRA +
                DAILY_LIFE_CHUNKS + DAILY_LIFE_CHUNKS_EXTRA +
                HEALTH_CHUNKS + HEALTH_CHUNKS_EXTRA +
                EDUCATION_CHUNKS + EDUCATION_CHUNKS_EXTRA +
                SOCIAL_CHUNKS + SOCIAL_CHUNKS_EXTRA
        )

        val CHUNKS_BY_ID: Map<String, Chunk> = CHUNKS.associateBy { it.id }

        init {
            require(CHUNKS_BY_ID.size == CHUNKS.size) { "Duplicate chunk id in the seed set" }
            require(ChunkTheme.entries.all { theme -> CHUNKS.any { it.theme == theme } }) {
                "Every ChunkTheme must have at least one chunk"
            }
        }
    }
}
