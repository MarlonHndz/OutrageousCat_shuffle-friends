package com.outrageouscat.shufflefriends.ui.navigation

import androidx.annotation.StringRes
import com.outrageouscat.shufflefriends.R

sealed class Screen(val route: String, @StringRes val title: Int) {
    object Home : Screen(
        route = "Home",
        title = R.string.screen_home
    )

    object Results : Screen(
        route = "Results",
        title = R.string.screen_results
    )

    object Settings : Screen(
        route = "Settings",
        title = R.string.screen_settings
    )

    object AboutUs : Screen(
        route = "About us",
        title = R.string.screen_about_us
    )
}
