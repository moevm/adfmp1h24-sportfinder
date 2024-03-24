package ru.moevm.sportfinder.screen.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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
import ru.moevm.sportfinder.screen.running.RunningListScreen
import ru.moevm.sportfinder.screen.running.RunningListViewModel
import ru.moevm.sportfinder.screen.settings.MainSettingsScreen
import ru.moevm.sportfinder.screen.settings.SettingsViewModel
import ru.moevm.sportfinder.screen.settings.UpdateProfileSettingsScreen
import ru.moevm.sportfinder.screen.sport_courts.SportCourtInfoScreen
import ru.moevm.sportfinder.screen.sport_courts.SportCourtInfoViewModel
import ru.moevm.sportfinder.screen.sport_courts.SportCourtListViewModel
import ru.moevm.sportfinder.screen.sport_courts.SportCourtMapScreen
import ru.moevm.sportfinder.screen.sport_courts.SportCourtMapViewModel
import ru.moevm.sportfinder.screen.sport_courts.SportCourtsListScreen
import ru.moevm.sportfinder.screen.training.TrainingListScreen
import ru.moevm.sportfinder.screen.training.TrainingListViewModel

@Composable
fun MainNavHost(
    navigationController: NavigationController,
    updateBottomBarVisible: (Boolean) -> Unit,
    updateTopBarType: (isVisible: Boolean, type: CommonTopBarType?) -> Unit,
) {
    NavHost(
        navController = navigationController.navHostController,
        startDestination = ScreensSubgraphs.AUTH.route
    ) {
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
                    .setTitle(
                        stringResource(id = R.string.app_title),
                        TextStyle(fontStyle = FontStyle.Italic, fontSize = 16.sp)
                    )
                    .addMenuButton(
                        R.drawable.ic_top_bar_settings,
                        navigationController::navigateToSettings
                    )
                    .build()
                updateTopBarType(true, topBarType)

                ProfileScreen(
                    state = state,
                    onTabSwitch = viewModel::switchTab
                )
            }
        }

        navigation(
            startDestination = Screen.SPORT_COURT_MAP_SCREEN.route,
            route = ScreensSubgraphs.SPORT_COURT.route
        ) {
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
                val navigateToSportCourtInfo = { courtId: Long ->
                    viewModel.onItemClicked(courtId)
                    navigationController.navigateToSportCourtInfo()
                }

                SportCourtsListScreen(
                    state = state,
                    onTextForFilterChanged = viewModel::onTextForFilterChanged,
                    onFilterApply = viewModel::onFilterApply,
                    navigateToSportCourtMapScreen = navigationController::navigateToSportCourtMap,
                    navigateToSportCourtInfoScreen = navigateToSportCourtInfo
                )
            }
            composable(route = Screen.SPORT_COURT_INFO_SCREEN.route) {
                val viewModel = hiltViewModel<SportCourtInfoViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val isShareUrlClicked by viewModel.isShareUrlClicked.collectAsStateWithLifecycle()
                updateBottomBarVisible(true)
                val topBarType = TopBarTypeBuilder()
                    .setBackButtonAsNavigationButton(navigationController::navigateBack)
                    .addMenuButton(R.drawable.ic_top_bar_share, viewModel::onShareClick)
                    .build()
                updateTopBarType(true, topBarType)

                SportCourtInfoScreen(
                    state = state,
                    isShareUrlClicked = isShareUrlClicked,
                    onSharedUrlShown = viewModel::onSharedUrlShown,
                    onFavoritesClicked = viewModel::onFavoritesClicked,
                )
            }
        }

        navigation(
            startDestination = Screen.TRAINING_LIST_SCREEN.route,
            route = ScreensSubgraphs.TRAINING.route
        ) {
            composable(route = Screen.TRAINING_LIST_SCREEN.route) {
                val viewModel = hiltViewModel<TrainingListViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                updateBottomBarVisible(true)
                updateTopBarType(false, null)

                TrainingListScreen(
                    state = state,
                    onTextForFilterChanged = viewModel::onTextForFilterChanged,
                    onFilterApply = viewModel::onFilterApply,
                    navigateToAddTrainingScreen = { /*TODO*/ },
                    navigateToTrainingInfoScreen = { /*TODO*/ }
                )
            }
        }

        navigation(
            startDestination = Screen.RUNNING_LIST_SCREEN.route,
            route = ScreensSubgraphs.RUNNING.route
        ) {
            composable(route = Screen.RUNNING_LIST_SCREEN.route) {
                val viewModel = hiltViewModel<RunningListViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                updateBottomBarVisible(true)
                updateTopBarType(false, null)

                RunningListScreen(
                    state = state,
                    onTextForFilterChanged = viewModel::onTextForFilterChanged,
                    onFilterApply = viewModel::onFilterApply,
                    navigateToRunningInfoScreen = { /*TODO*/ },
                    navigateToRunningCreateScreen = { /*TODO*/ }
                )
            }
        }

        navigation(
            startDestination = Screen.SETTINGS_MAIN_SCREEN.route,
            route = ScreensSubgraphs.SETTINGS.route
        ) {
            composable(route = Screen.SETTINGS_MAIN_SCREEN.route) {
                val viewModel = hiltViewModel<SettingsViewModel>()
                updateBottomBarVisible(false)
                val topBarType = TopBarTypeBuilder()
                    .setBackButtonAsNavigationButton(navigationController::navigateBack)
                    .build()
                updateTopBarType(true, topBarType)
                val toast = Toast.makeText(
                    LocalContext.current,
                    stringResource(id = R.string.settings_screen_common_successfully_logout),
                    Toast.LENGTH_SHORT
                )

                MainSettingsScreen(
                    onLogOutClick = {
                        viewModel.onLogOutClicked {
                            toast.show()
                            // TODO предусмотреть возможно ошибочное поведение при нажатии Back
                            navigationController.navigateToAuthScreen()
                        }
                    },
                    navigateToUpdateProfile = navigationController::navigateToSettingsUpdateProfile
                )
            }
            composable(route = Screen.SETTINGS_UPDATE_PROFILE_SCREEN.route) {
                val viewModel = hiltViewModel<SettingsViewModel>()
                val state by viewModel.updateProfileState.collectAsStateWithLifecycle()
                updateBottomBarVisible(false)
                val topBarType = TopBarTypeBuilder()
                    .setBackButtonAsNavigationButton(navigationController::navigateBack)
                    .build()
                updateTopBarType(true, topBarType)
                val toast = Toast.makeText(
                    LocalContext.current,
                    stringResource(id = R.string.settings_screen_update_profile_successfully_saved),
                    Toast.LENGTH_SHORT
                )

                UpdateProfileSettingsScreen(
                    state = state,
                    onImageUrlEntered = viewModel::onImageUrlEntered,
                    onNameEntered = viewModel::onNameEntered,
                    onSaveClicked = {
                        toast.show()
                        viewModel.onSaveClicked()
                        navigationController.navigateBack()
                    }
                )
            }
        }
    }
}