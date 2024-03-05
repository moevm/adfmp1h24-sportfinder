package ru.moevm.sportfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.moevm.sportfinder.screen.navigation.MainNavHost
import ru.moevm.sportfinder.screen.navigation.NavigationController
import ru.moevm.sportfinder.ui.theme.SportFinderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportFinderTheme {
                val navHostController = rememberNavController()
                val navigationController = remember { NavigationController(navHostController) }

                Scaffold { paddingValues ->
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        MainNavHost(navigationController = navigationController)
                    }
                }
            }
        }
    }
}