package ru.moevm.sportfinder.screen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.screen.auth.AuthorizationScreen
import ru.moevm.sportfinder.screen.auth.AuthorizationViewModel
import ru.moevm.sportfinder.screen.auth.RegistrationScreen
import ru.moevm.sportfinder.screen.auth.RegistrationViewModel
import ru.moevm.sportfinder.screen.common_components.common_top_bar.CommonTopBarType
import ru.moevm.sportfinder.screen.common_components.common_top_bar.TopBarTypeBuilder
import ru.moevm.sportfinder.screen.profile.ProfileScreen
import ru.moevm.sportfinder.screen.profile.ProfileViewModel
import ru.moevm.sportfinder.screen.sport_courts.SportCourtListViewModel
import ru.moevm.sportfinder.screen.sport_courts.SportCourtMapScreen
import ru.moevm.sportfinder.screen.sport_courts.SportCourtMapViewModel
import ru.moevm.sportfinder.screen.sport_courts.SportCourtsListScreen

@Composable
fun MainNavHost(
    navigationController: NavigationController,
    updateBottomBarVisible: (Boolean) -> Unit,
    updateTopBarType: (isVisible: Boolean, type: CommonTopBarType?) -> Unit,
) {
    NavHost(navController = navigationController.navHostController, startDestination = ScreensSubgraphs.AUTH.route) {
        navigation(
            startDestination = Screen.AUTH_SCREEN.route,
            route = ScreensSubgraphs.AUTH.route
        ) {
            composable(route = Screen.AUTH_SCREEN.route) {
                val viewModel = hiltViewModel<AuthorizationViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                updateBottomBarVisible(false)
                updateTopBarType(false, null)

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
                updateBottomBarVisible(false)
                updateTopBarType(false, null)

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
                updateBottomBarVisible(true)
                val topBarType = TopBarTypeBuilder()
                    .setTitle(stringResource(id = R.string.app_title), TextStyle(fontStyle = FontStyle.Italic, fontSize = 16.sp))
                    .addMenuButton(R.drawable.ic_top_bar_settings, navigationController::navigateToSettings)
                    .build()
                updateTopBarType(true, topBarType)

                ProfileScreen(
                    state = state,
                    onTabSwitch = viewModel::switchTab
                )
            }
        }

        navigation(startDestination = Screen.SPORT_COURT_MAP_SCREEN.route, route = ScreensSubgraphs.SPORT_COURT.route) {
            composable(route = Screen.SPORT_COURT_MAP_SCREEN.route) {
                val viewModel = hiltViewModel<SportCourtMapViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                updateBottomBarVisible(true)
                updateTopBarType(false, null)

                SportCourtMapScreen(
                    startPoint = viewModel.startPoint,
                    state = state,
                    onTextForFilterChanged = viewModel::onTextForFilterChanged,
                    onFilterApply = viewModel::onFilterApply,
                    navigateToSportCourtListScreen = navigationController::navigateToSportCourtList
                )
            }
            composable(route = Screen.SPORT_COURT_LIST_SCREEN.route) {
                val viewModel = hiltViewModel<SportCourtListViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                updateBottomBarVisible(true)
                updateTopBarType(false, null)

                SportCourtsListScreen(
                    state = state,
                    onTextForFilterChanged = viewModel::onTextForFilterChanged,
                    onFilterApply = viewModel::onFilterApply,
                    navigateToSportCourtMapScreen = navigationController::navigateToSportCourtMap
                )
            }
        }
    }
}