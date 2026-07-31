package by.freiding.braindrop

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import by.freiding.braindrop.core.navigation.Routes
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.feature.home.presentation.screen.HomeScreen
import by.freiding.braindrop.feature.irregularverbs.presentation.detail.VerbDetailScreen
import by.freiding.braindrop.feature.irregularverbs.presentation.list.VerbListScreen
import by.freiding.braindrop.feature.irregularverbs.presentation.quiz.QuizScreen

@Composable
fun App() {
    BrainDropTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Routes.Home) {
            composable<Routes.Home> {
                HomeScreen(navController)
            }
            composable<Routes.Profile> {
                // ProfileScreen(navController)
            }
            composable<Routes.IrregularVerbsList> {
                VerbListScreen(navController)
            }
            composable<Routes.IrregularVerbDetail> { backStackEntry ->
                val route = backStackEntry.toRoute<Routes.IrregularVerbDetail>()
                VerbDetailScreen(verbId = route.verbId, navController = navController)
            }
            composable<Routes.IrregularVerbsQuiz> { backStackEntry ->
                val route = backStackEntry.toRoute<Routes.IrregularVerbsQuiz>()
                QuizScreen(mode = route.mode, navController = navController)
            }
        }
    }
}
