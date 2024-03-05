package ru.moevm.sportfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.moevm.sportfinder.screen.auth.AuthorizationScreen
import ru.moevm.sportfinder.screen.auth.AuthorizationViewModel
import ru.moevm.sportfinder.ui.theme.SportFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportFinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewMode = hiltViewModel<AuthorizationViewModel>()
                    val state by viewMode.state.collectAsStateWithLifecycle()

                    AuthorizationScreen(
                        state = state,
                        onLoginEnter = viewMode::updateLogin,
                        onPasswordEnter = viewMode::updatePassword,
                        onSingInClicked = viewMode::trySignIn,
                        onNavigateToRegClicked = {}
                    )
                }
            }
        }
    }
}