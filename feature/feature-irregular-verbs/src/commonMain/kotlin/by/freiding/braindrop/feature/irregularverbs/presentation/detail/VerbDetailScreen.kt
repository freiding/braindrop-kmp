package by.freiding.braindrop.feature.irregularverbs.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.feature.irregularverbs.domain.model.IrregularVerb
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbExample
import by.freiding.braindrop.feature.irregularverbs.Res
import by.freiding.braindrop.feature.irregularverbs.error_generic
import by.freiding.braindrop.feature.irregularverbs.nav_back
import by.freiding.braindrop.feature.irregularverbs.verb_detail_examples
import by.freiding.braindrop.feature.irregularverbs.verb_detail_mark_learned
import by.freiding.braindrop.feature.irregularverbs.verb_detail_unmark_learned
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerbDetailScreen(
    verbId: String,
    navController: NavController,
    viewModel: VerbDetailViewModel = koinViewModel(parameters = { parametersOf(verbId) }),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is VerbDetailUiEffect.NavigateBack -> navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.verbWithProgress?.verb?.baseForm ?: "Irregular Verb") },
                navigationIcon = {
                    Text(
                        text = stringResource(Res.string.nav_back),
                        modifier = Modifier
                            .clickable { viewModel.onEvent(VerbDetailUiEvent.NavigateBack) }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
            )
        },
    ) { innerPadding ->
        when {
            state.isLoading -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) { CircularProgressIndicator() }

            state.error != null -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) { Text(text = state.error ?: stringResource(Res.string.error_generic)) }

            state.verbWithProgress != null -> {
                val item = state.verbWithProgress!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    VerbFormsCard(verb = item.verb)
                    ExamplesCard(examples = item.verb.examples)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (item.progress.isLearned) {
                        OutlinedButton(
                            onClick = { viewModel.onEvent(VerbDetailUiEvent.ToggleLearned) },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(stringResource(Res.string.verb_detail_unmark_learned))
                        }
                    } else {
                        Button(
                            onClick = { viewModel.onEvent(VerbDetailUiEvent.ToggleLearned) },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(stringResource(Res.string.verb_detail_mark_learned))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VerbFormsCard(verb: IrregularVerb) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = verb.translation,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                VerbFormColumn(label = "Base Form", form = verb.baseForm)
                VerbFormColumn(label = "Past Simple", form = verb.pastSimple)
                VerbFormColumn(label = "Past Participle", form = verb.pastParticiple)
            }
        }
    }
}

@Composable
private fun VerbFormColumn(label: String, form: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
        )
        Text(
            text = form,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Composable
private fun ExamplesCard(examples: List<VerbExample>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = stringResource(Res.string.verb_detail_examples),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
            )
            examples.forEach { example ->
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = example.english,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = example.russian,
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}
