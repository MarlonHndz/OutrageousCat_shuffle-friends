package com.outrageouscat.shufflefriends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.outrageouscat.shufflefriends.ui.navigation.NavigationHost
import com.outrageouscat.shufflefriends.ui.theme.ShuffleFriendsTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinContext {
                ShuffleFriendsTheme {
                    val navController = rememberNavController()
                    NavigationHost(
                        context = this@MainActivity,
                        modifier = Modifier.fillMaxSize(),
                        navController = navController
                    )
                }
            }
        }
    }
}
