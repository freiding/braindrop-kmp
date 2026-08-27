package by.freiding.braindrop.feature.phrasalverbs.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropButton
import by.freiding.braindrop.core.ui.component.BrainDropButtonStyle
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbMeaning
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbRegister
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbWithProgress
import by.freiding.braindrop.feature.phrasalverbs.presentation.list.displayName
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PhrasalVerbDetailScreen(
    verbId: String,
    navController: NavController,
    viewModel: PhrasalVerbDetailViewModel = koinViewModel { parametersOf(verbId) },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is PhrasalVerbDetailUiEffect.NavigateBack -> navController.popBackStack()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        DetailHeader(
            item = state.item,
            onBack = { viewModel.onEvent(PhrasalVerbDetailUiEvent.NavigateBack) },
            onToggleLearned = { viewModel.onEvent(PhrasalVerbDetailUiEvent.ToggleLearned) },
        )

        when {
            state.error != null -> ErrorStatusCard(
                title = "Не удалось загрузить глагол",
                body = state.error ?: "",
                retryText = "Повторить",
                onRetry = { viewModel.reload() },
                secondaryText = "Назад",
                onSecondary = { viewModel.onEvent(PhrasalVerbDetailUiEvent.NavigateBack) },
            )
            state.isLoading -> Box(Modifier.fillMaxSize())
            state.item != null -> DetailContent(item = state.item!!)
        }
    }
}

@Composable
private fun DetailHeader(
    item: PhrasalVerbWithProgress?,
    onBack: () -> Unit,
    onToggleLearned: () -> Unit,
) {
    val isLearned = item?.progress?.isLearned == true
    val semantics = BrainDropTheme.semantics

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BrainDropTheme.spacing.xs, vertical = BrainDropTheme.spacing.xxs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BrainDropIconButton(onClick = onBack, contentDescription = "Назад") {
                BrainDropIcons.ChevronLeft(iconSize = 22.dp, tint = MaterialTheme.colorScheme.onSurface)
            }
            Column(modifier = Modifier.weight(1f).padding(start = BrainDropTheme.spacing.xxs)) {
                if (item != null) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                                append(item.verb.verb)
                            }
                            append(" ")
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append(item.verb.particle)
                            }
                        },
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = item.verb.category.displayName(),
                        style = BrainDropTheme.type.label,
                        color = semantics.ink400,
                    )
                }
            }
            BrainDropIconButton(
                onClick = onToggleLearned,
                contentDescription = if (isLearned) "Снять отметку" else "Отметить как изученный",
            ) {
                if (isLearned) {
                    Box(
                        modifier = Modifier.size(28.dp).background(semantics.correct, BrainDropTheme.shapes.chip),
                        contentAlignment = Alignment.Center,
                    ) {
                        BrainDropIcons.Check(iconSize = 15.dp, tint = androidx.compose.ui.graphics.Color.White, strokeWidth = 2.4.dp)
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .border(2.dp, semantics.ink400, BrainDropTheme.shapes.chip),
                    )
                }
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
    }
}

@Composable
private fun DetailContent(item: PhrasalVerbWithProgress) {
    val semantics = BrainDropTheme.semantics

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BrainDropTheme.spacing.md, vertical = BrainDropTheme.spacing.sm),
                horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
            ) {
                BadgeChip(
                    text = if (item.verb.isSeparable) "separable" else "inseparable",
                    containerColor = if (item.verb.isSeparable) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                    contentColor = if (item.verb.isSeparable) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        semantics.ink500
                    },
                )
            }
        }

        itemsIndexed(item.verb.meanings) { index, meaning ->
            MeaningCard(index = index + 1, meaning = meaning)
            if (index < item.verb.meanings.lastIndex) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.padding(horizontal = BrainDropTheme.spacing.md),
                )
            }
        }

        item { Spacer(Modifier.height(BrainDropTheme.spacing.xl)) }
    }
}

@Composable
private fun MeaningCard(index: Int, meaning: PhrasalVerbMeaning) {
    val semantics = BrainDropTheme.semantics

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = BrainDropTheme.spacing.md, vertical = BrainDropTheme.spacing.sm),
        verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .background(MaterialTheme.colorScheme.primary, BrainDropTheme.shapes.chip),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = index.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = androidx.compose.ui.graphics.Color.White,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
            if (meaning.register != PhrasalVerbRegister.NEUTRAL) {
                BadgeChip(
                    text = meaning.register.name.lowercase(),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = semantics.ink500,
                )
            }
        }

        Text(
            text = meaning.definition,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = meaning.translation,
            style = BrainDropTheme.type.translation,
            color = semantics.ink500,
        )

        if (meaning.examples.isNotEmpty()) {
            Spacer(Modifier.height(2.dp))
            Text(
                text = "ПРИМЕРЫ",
                style = BrainDropTheme.type.label,
                color = semantics.ink400,
            )
            meaning.examples.forEach { example ->
                ExampleRow(english = example.english, russian = example.russian)
            }
        }
    }
}

@Composable
private fun ExampleRow(english: String, russian: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = BrainDropTheme.spacing.sm, vertical = BrainDropTheme.spacing.xs),
    ) {
        Text(text = english, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Text(
            text = russian,
            style = BrainDropTheme.type.translation,
            color = BrainDropTheme.semantics.ink500,
            modifier = Modifier.padding(top = 2.dp),
        )
    }
}

@Composable
private fun BadgeChip(text: String, containerColor: androidx.compose.ui.graphics.Color, contentColor: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(containerColor)
            .padding(horizontal = 8.dp, vertical = 3.dp),
    ) {
        Text(text = text, style = BrainDropTheme.type.label, color = contentColor)
    }
}
