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
import ru.moevm.sportfinder.screen.auth.RegistrationScreen
import ru.moevm.sportfinder.screen.auth.RegistrationViewModel
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
                    val viewMode = hiltViewModel<RegistrationViewModel>()
                    val state by viewMode.state.collectAsStateWithLifecycle()

                    RegistrationScreen(
                        state = state,
                        onLoginEnter = viewMode::updateLogin,
                        onPasswordEnter = viewMode::updatePassword,
                        onSingUpClicked = viewMode::trySignUp,
                        onNavigateToAuthClicked = {}
                    )
                }
            }
        }
    }
}