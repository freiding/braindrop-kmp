package by.freiding.braindrop.feature.vocabulary.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkError
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkExample

/** A usage sentence with the chunk highlighted, plus its translation — the "чанк в контексте" card. */
@Composable
internal fun ChunkExampleCard(
    example: ChunkExample,
    chunk: Chunk,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = BrainDropTheme.spacing.sm, vertical = BrainDropTheme.spacing.xs),
    ) {
        Text(
            text = highlightChunk(
                sentence = example.english,
                chunk = chunk.text,
                headword = chunk.headword,
                highlightColor = MaterialTheme.colorScheme.primary,
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = example.russian,
            style = BrainDropTheme.type.translation,
            color = BrainDropTheme.semantics.ink500,
            modifier = Modifier.padding(top = 2.dp),
        )
    }
}

/** The "так не говорят" card — a common collocation error and why it's wrong. */
@Composable
internal fun CommonErrorCard(
    error: ChunkError,
    modifier: Modifier = Modifier,
) {
    val semantics = BrainDropTheme.semantics
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(semantics.incorrectTint)
            .padding(horizontal = BrainDropTheme.spacing.sm, vertical = BrainDropTheme.spacing.xs),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BrainDropIcons.Close(iconSize = 14.dp, tint = semantics.incorrect, strokeWidth = 2.4.dp)
            Text(
                text = error.wrong,
                style = MaterialTheme.typography.bodyMedium,
                color = semantics.incorrectInk,
            )
        }
        Spacer(Modifier.height(2.dp))
        Text(
            text = error.explanation,
            style = BrainDropTheme.type.translation,
            color = semantics.incorrectInk,
        )
    }
}
