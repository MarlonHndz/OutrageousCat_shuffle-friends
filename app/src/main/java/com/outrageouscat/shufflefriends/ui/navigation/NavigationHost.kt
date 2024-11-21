package com.outrageouscat.shufflefriends.ui.navigation

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.outrageouscat.shufflefriends.ui.screens.home.HomeScreen
import com.outrageouscat.shufflefriends.ui.screens.results.ResultsScreen
import com.outrageouscat.shufflefriends.ui.screens.settings.SettingsScreen

@Composable
fun NavigationHost(
    context: Context,
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                context = context,
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }

        composable(Screen.Results.route) {
            ResultsScreen(
                modifier = Modifier.fillMaxSize(),
                onBack = { navController.navigateUp() },
                navController = navController
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                context = context,
                modifier = Modifier.fillMaxSize(),
                onBack = { navController.navigateUp() }
            )
        }
    }
}
