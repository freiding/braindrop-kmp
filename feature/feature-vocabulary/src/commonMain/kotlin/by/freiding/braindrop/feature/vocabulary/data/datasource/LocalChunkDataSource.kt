package by.freiding.braindrop.feature.vocabulary.data.datasource

import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.DAILY_LIFE_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.FEELINGS_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.HEALTH_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.MONEY_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.TRAVEL_CHUNKS
import by.freiding.braindrop.feature.vocabulary.data.datasource.seed.WORK_CHUNKS
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

    /** Number of distinct themes with at least one chunk — shown on the Home category card. */
    fun themeCount(): Int = CHUNKS.mapTo(mutableSetOf(), Chunk::theme).size

    private companion object {
        val CHUNKS: List<Chunk> =
            (WORK_CHUNKS + TRAVEL_CHUNKS + MONEY_CHUNKS + FEELINGS_CHUNKS + DAILY_LIFE_CHUNKS + HEALTH_CHUNKS)

        val CHUNKS_BY_ID: Map<String, Chunk> = CHUNKS.associateBy { it.id }

        init {
            require(CHUNKS_BY_ID.size == CHUNKS.size) { "Duplicate chunk id in the seed set" }
            require(ChunkTheme.entries.all { theme -> CHUNKS.any { it.theme == theme } }) {
                "Every ChunkTheme must have at least one chunk"
            }
        }
    }
}
