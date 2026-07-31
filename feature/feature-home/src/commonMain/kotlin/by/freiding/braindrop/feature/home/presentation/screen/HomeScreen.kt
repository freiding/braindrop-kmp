package by.freiding.braindrop.feature.home.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.navigation.Routes
import by.freiding.braindrop.feature.home.presentation.components.StudyCategoryCard
import by.freiding.braindrop.feature.home.presentation.viewmodel.HomeUiEffect
import by.freiding.braindrop.feature.home.presentation.viewmodel.HomeUiEvent
import by.freiding.braindrop.feature.home.presentation.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LifecycleResumeEffect(Unit) {
        viewModel.reload()
        onPauseOrDispose { }
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is HomeUiEffect.NavigateToCategory -> when (effect.categoryId) {
                    "irregular_verbs" -> navController.navigate(Routes.IrregularVerbsList)
                    else -> Unit
                }
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("BrainDrop") }) },
    ) { innerPadding ->
        when {
            state.isLoading -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }

            state.error != null -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = state.error ?: "Unknown error")
            }

            else -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(state.categories, key = { it.id }) { category ->
                    StudyCategoryCard(
                        category = category,
                        onClick = { viewModel.onEvent(HomeUiEvent.CategoryClicked(category.id)) },
                    )
                }
            }
        }
    }
}
