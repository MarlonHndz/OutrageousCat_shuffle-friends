package com.outrageouscat.shufflefriends.ui.navigation

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.outrageouscat.shufflefriends.ui.HomeScreen
import com.outrageouscat.shufflefriends.ui.ResultsScreen
import kotlinx.serialization.json.Json

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

        composable(
            route = Screen.Results.route,
            arguments = listOf(
                navArgument("results") {
                    type = NavType.StringType
                }
            )
        ) {  backStackEntry ->
            val resultsJson = backStackEntry.arguments?.getString("results") ?: "{}"
            val type = object : TypeToken<Map<String, String>>() {}.type
            val results: Map<String, String> = Gson().fromJson(resultsJson, type)

            ResultsScreen(
                modifier = Modifier.fillMaxSize(),
                results = results,
                onBack = { navController.navigateUp() }
            )
        }
    }
}
