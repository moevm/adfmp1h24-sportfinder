package ru.moevm.sportfinder.screen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.moevm.sportfinder.screen.auth.AuthorizationScreen
import ru.moevm.sportfinder.screen.auth.AuthorizationViewModel
import ru.moevm.sportfinder.screen.auth.RegistrationScreen
import ru.moevm.sportfinder.screen.auth.RegistrationViewModel
import ru.moevm.sportfinder.screen.profile.ProfileScreen
import ru.moevm.sportfinder.screen.profile.ProfileViewModel

@Composable
fun MainNavHost(
    navigationController: NavigationController
) {
    NavHost(navController = navigationController.navHostController, startDestination = ScreensSubgraphs.AUTH.route) {
        navigation(
            startDestination = Screen.AUTH_SCREEN.route,
            route = ScreensSubgraphs.AUTH.route
        ) {
            composable(route = Screen.AUTH_SCREEN.route) {
                val viewModel = hiltViewModel<AuthorizationViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                AuthorizationScreen(
                    state = state,
                    onLoginEnter = viewModel::updateLogin,
                    onPasswordEnter = viewModel::updatePassword,
                    onSingInClicked = viewModel::trySignIn,
                    onNavigateToRegClicked = navigationController::navigateToRegistrationScreen,
                    onNavigateToProfileScreen = navigationController::navigateFromAuthToProfile,
                )
            }
            composable(route = Screen.REG_SCREEN.route) {
                val viewModel = hiltViewModel<RegistrationViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                RegistrationScreen(
                    state = state,
                    onLoginEnter = viewModel::updateLogin,
                    onPasswordEnter = viewModel::updatePassword,
                    onSingUpClicked = viewModel::trySignUp,
                    onNavigateToAuthClicked = navigationController::navigateToAuthScreen,
                    onNavigateToProfileScreen = navigationController::navigateFromAuthToProfile,
                )
            }
        }

        navigation(
            startDestination = Screen.PROFILE_SCREEN.route,
            route = ScreensSubgraphs.PROFILE.route
        ) {
            composable(route = Screen.PROFILE_SCREEN.route) {
                val viewModel = hiltViewModel<ProfileViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                ProfileScreen(
                    state = state,
                    onTabSwitch = viewModel::switchTab
                )
            }
        }
    }
}