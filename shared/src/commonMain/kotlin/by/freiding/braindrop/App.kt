package by.freiding.braindrop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import by.freiding.braindrop.core.analytics.AnalyticsTracker
import by.freiding.braindrop.core.navigation.Routes
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropNavTab
import by.freiding.braindrop.core.ui.component.BrainDropNavigationBar
import by.freiding.braindrop.feature.home.presentation.screen.HomeScreen
import by.freiding.braindrop.feature.irregularverbs.presentation.detail.VerbDetailScreen
import by.freiding.braindrop.feature.irregularverbs.presentation.list.VerbListScreen
import by.freiding.braindrop.feature.irregularverbs.presentation.quiz.QuizScreen
import by.freiding.braindrop.feature.profile.presentation.screen.ProfileScreen
import by.freiding.braindrop.feature.profile.presentation.screen.ProgressScreen
import by.freiding.braindrop.feature.tenses.presentation.cheatsheet.TenseCheatSheetScreen
import by.freiding.braindrop.feature.tenses.presentation.comparison.TenseComparisonsScreen
import by.freiding.braindrop.feature.tenses.presentation.detail.TenseDetailScreen
import by.freiding.braindrop.feature.tenses.presentation.list.TensesListScreen
import by.freiding.braindrop.feature.tenses.presentation.quiz.TensesQuizScreen
import org.koin.compose.koinInject

@Composable
fun App() {
    BrainDropTheme {
        val analytics = koinInject<AnalyticsTracker>()
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val destination = backStackEntry?.destination

        // One screen_view per destination change; the route's serial name (e.g.
        // "…Routes.TenseDetail/{tenseId}") is trimmed to a bare screen name.
        LaunchedEffect(destination?.route) {
            val route = destination?.route ?: return@LaunchedEffect
            analytics.logScreenView(route.substringBefore('/').substringAfterLast('.'))
        }

        // The tab bar is visible only on the three top-level tabs — list/detail/quiz screens go on top, without it.
        val currentTab = when {
            destination?.hasRoute<Routes.Home>() == true -> BrainDropNavTab.LEARN
            destination?.hasRoute<Routes.Progress>() == true -> BrainDropNavTab.PROGRESS
            destination?.hasRoute<Routes.Profile>() == true -> BrainDropNavTab.PROFILE
            else -> null
        }

        Scaffold(
            bottomBar = {
                if (currentTab != null) {
                    BrainDropNavigationBar(
                        selected = currentTab,
                        onSelect = { tab -> navigateToTab(navController, tab) },
                    )
                }
            },
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(navController = navController, startDestination = Routes.Home) {
                    composable<Routes.Home> {
                        HomeScreen(navController)
                    }
                    composable<Routes.Progress> {
                        ProgressScreen()
                    }
                    composable<Routes.Profile> {
                        ProfileScreen()
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
                    composable<Routes.TensesList> {
                        TensesListScreen(navController)
                    }
                    composable<Routes.TenseDetail> { backStackEntry ->
                        val route = backStackEntry.toRoute<Routes.TenseDetail>()
                        TenseDetailScreen(tenseId = route.tenseId, navController = navController)
                    }
                    composable<Routes.TenseComparisons> { backStackEntry ->
                        val route = backStackEntry.toRoute<Routes.TenseComparisons>()
                        TenseComparisonsScreen(initialComparisonId = route.comparisonId, navController = navController)
                    }
                    composable<Routes.TenseCheatSheet> {
                        TenseCheatSheetScreen(navController)
                    }
                    composable<Routes.TensesQuiz> { backStackEntry ->
                        val route = backStackEntry.toRoute<Routes.TensesQuiz>()
                        TensesQuizScreen(mode = route.mode, navController = navController)
                    }
                }
            }
        }
    }
}

private fun navigateToTab(
    navController: NavController,
    tab: BrainDropNavTab,
) {
    val route = when (tab) {
        BrainDropNavTab.LEARN -> Routes.Home
        BrainDropNavTab.PROGRESS -> Routes.Progress
        BrainDropNavTab.PROFILE -> Routes.Profile
    }
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
