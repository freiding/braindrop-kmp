package by.freiding.braindrop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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

@Composable
fun App() {
    BrainDropTheme {
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val destination = backStackEntry?.destination

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
