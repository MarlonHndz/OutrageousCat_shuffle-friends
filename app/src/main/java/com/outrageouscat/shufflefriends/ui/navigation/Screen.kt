package com.outrageouscat.shufflefriends.ui.navigation

import androidx.annotation.StringRes

sealed class Screen(val route: String, val title: String) {
    object Home : Screen(
        route = "Home",
        title = "Participants list"
    )

    object Results : Screen (
        route = "Results",
        title = "Shuffle results"
    )
}
