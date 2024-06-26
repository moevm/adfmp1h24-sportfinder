package ru.moevm.sportfinder.screen.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.screen.about.AboutScreen
import ru.moevm.sportfinder.screen.auth.AuthorizationScreen
import ru.moevm.sportfinder.screen.auth.AuthorizationViewModel
import ru.moevm.sportfinder.screen.auth.RegistrationScreen
import ru.moevm.sportfinder.screen.auth.RegistrationViewModel
import ru.moevm.sportfinder.screen.common_components.common_top_bar.CommonTopBarType
import ru.moevm.sportfinder.screen.common_components.common_top_bar.CommonTopBarTypeBuilder
import ru.moevm.sportfinder.screen.profile.ProfileScreen
import ru.moevm.sportfinder.screen.profile.ProfileViewModel
import ru.moevm.sportfinder.screen.running.RunningCreateScreen
import ru.moevm.sportfinder.screen.running.RunningCreateViewModel
import ru.moevm.sportfinder.screen.running.RunningInfoScreen
import ru.moevm.sportfinder.screen.running.RunningInfoViewModel
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
import ru.moevm.sportfinder.screen.training.TrainingCreateScreen
import ru.moevm.sportfinder.screen.training.TrainingCreateViewModel
import ru.moevm.sportfinder.screen.training.TrainingInfoScreen
import ru.moevm.sportfinder.screen.training.TrainingInfoViewModel
import ru.moevm.sportfinder.screen.training.TrainingListScreen
import ru.moevm.sportfinder.screen.training.TrainingListViewModel

@Composable
fun MainNavHost(
    navigationController: NavigationController,
    updateBottomBarVisible: (Boolean) -> Unit,
    updateTopBarType: (isVisible: Boolean, type: CommonTopBarType?) -> Unit,
    shareText: (text: String) -> Unit
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
                LaunchedEffect(true) {
                    viewModel.trySignInFromStart()
                }

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
                val topBarType = CommonTopBarTypeBuilder()
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

                LaunchedEffect(true) {
                    viewModel.updateProfileData()
                }

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
                    navigateToSportCourtListScreen = navigationController::navigateToSportCourtList,
                    navigateToSportCourtInfoScreen = { courtId -> navigationController.navigateToSportCourtInfo(courtId) }
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
                    navigateToSportCourtMapScreen = navigationController::navigateToSportCourtMap,
                    navigateToSportCourtInfoScreen = { courtId -> navigationController.navigateToSportCourtInfo(courtId) }
                )
            }
            composable(route = Screen.SPORT_COURT_INFO_SCREEN.route + "/{sportCourtId}",
                arguments = listOf(navArgument("sportCourtId") {
                    type = NavType.IntType
                })
            ) {
                val viewModel = hiltViewModel<SportCourtInfoViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                updateBottomBarVisible(true)
                val topBarType = CommonTopBarTypeBuilder()
                    .setBackButtonAsNavigationButton(navigationController::navigateBack)
                    .addMenuButton(R.drawable.ic_top_bar_share) {
                        shareText(viewModel.getSharingText())
                    }
                    .build()
                updateTopBarType(true, topBarType)

                SportCourtInfoScreen(
                    state = state,
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
                LaunchedEffect(true) {
                    viewModel.updateListOfTrainings()
                }
                TrainingListScreen(
                    state = state,
                    onTextForFilterChanged = viewModel::onTextForFilterChanged,
                    onFilterApply = viewModel::onFilterApply,
                    navigateToCreateTrainingScreen = navigationController::navigateToTrainingCreate,
                    navigateToTrainingInfoScreen = { id -> navigationController.navigateToTrainingInfo(id) }
                )
            }

            composable(route = Screen.TRAINING_CREATE_SCREEN.route) {
                val viewModel = hiltViewModel<TrainingCreateViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                updateBottomBarVisible(true)
                val topBarType = CommonTopBarTypeBuilder()
                    .setBackButtonAsNavigationButton(navigationController::navigateBack)
                    .build()
                updateTopBarType(true, topBarType)

                TrainingCreateScreen(
                    state = state,
                    availableTags = viewModel.availableTags,
                    onChangeNameClick = viewModel::onChangeNameClick,
                    onChangeName = viewModel::onChangeName,
                    onShowSelectTagsDialogClick = viewModel::onShowSelectTagsDialogClick,
                    onSaveSelectedTagsClick = viewModel::onSaveSelectedTagsClick,
                    onDismissSelectTagsDialogClick = viewModel::onDismissSelectTagsDialogClick,
                    onRemoveTagClick = viewModel::onRemoveTagClick,
                    onChangeDescriptionClick = viewModel::onChangeDescriptionClick,
                    onChangeDescription = viewModel::onChangeDescription,
                    onSaveClick = {
                        viewModel.onSaveClick(onSuccess = {
                            navigationController.navigateToTraining()
                        })
                    }
                )
            }

            composable(route = Screen.TRAINING_INFO_SCREEN.route + "/{trainingId}",
                arguments = listOf(navArgument("trainingId") {
                    type = NavType.IntType
                })
            ) {
                val viewModel = hiltViewModel<TrainingInfoViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                updateBottomBarVisible(true)
                val topBarType = CommonTopBarTypeBuilder()
                    .setBackButtonAsNavigationButton(navigationController::navigateBack)
                    .addMenuButton(R.drawable.ic_top_bar_share) {
                        shareText(viewModel.getSharingText())
                    }
                    .build()
                updateTopBarType(true, topBarType)
                LaunchedEffect(true) {
                    viewModel.updateTrainingInfoState()
                }

                TrainingInfoScreen(
                    state = state,
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

                LaunchedEffect(true) {
                    viewModel.updateListOfRunning()
                }

                RunningListScreen(
                    state = state,
                    onTextForFilterChanged = viewModel::onTextForFilterChanged,
                    onFilterApply = viewModel::onFilterApply,
                    navigateToRunningInfoScreen = { id -> navigationController.navigateToRunningInfo(id) },
                    navigateToRunningCreateScreen = navigationController::navigateToRunningCreate
                )
            }

            composable(route = Screen.RUNNING_CREATE_SCREEN.route) {
                val viewModel = hiltViewModel<RunningCreateViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                updateBottomBarVisible(true)
                val topBarType = CommonTopBarTypeBuilder()
                    .setBackButtonAsNavigationButton(navigationController::navigateBack)
                    .build()
                updateTopBarType(true, topBarType)

                RunningCreateScreen(
                    state = state,
                    startPoint = viewModel.startPoint,
                    onTitleChanged = viewModel::onTitleChanged,
                    onCustomTagChanged = viewModel::onCustomTagChanged,
                    onCustomTagAdded = viewModel::onCustomTagAdded,
                    addPoint = viewModel::addPoint,
                    removePoint = viewModel::removePoint,
                    onShowSelectTagsDialogClick = viewModel::onShowSelectTagsDialogClick,
                    onSaveTagsDialogClick = viewModel::onSaveTagsDialogClick,
                    onDismissTagsDialogClick = viewModel::onDismissTagsDialogClick,
                    onRemoveTagClick = viewModel::onRemoveTagClick,
                    onSaveClick = {
                        viewModel.onSaveClick(onSuccess = {
                            navigationController.navigateToRunning()
                        })
                    },
                )
            }

            composable(route = Screen.RUNNING_INFO_SCREEN.route + "/{runningId}",
                arguments = listOf(navArgument("runningId") {
                    type = NavType.IntType
                })
            ) {
                val viewModel = hiltViewModel<RunningInfoViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                updateBottomBarVisible(true)
                val topBarType = CommonTopBarTypeBuilder()
                    .setBackButtonAsNavigationButton(navigationController::navigateBack)
                    .addMenuButton(R.drawable.ic_top_bar_share) {
                        shareText(viewModel.getSharingText())
                    }
                    .build()
                updateTopBarType(true, topBarType)

                LaunchedEffect(true) {
                    viewModel.initState()
                }

                RunningInfoScreen(
                    state = state
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
                val topBarType = CommonTopBarTypeBuilder()
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
                            navigationController.navigateToAuthScreen()
                        }
                    },
                    navigateToUpdateProfile = navigationController::navigateToSettingsUpdateProfile,
                    navigateToAboutScreen = navigationController::navigateToAboutScreen
                )
            }
            composable(route = Screen.SETTINGS_UPDATE_PROFILE_SCREEN.route) {
                val viewModel = hiltViewModel<SettingsViewModel>()
                val state by viewModel.updateProfileState.collectAsStateWithLifecycle()
                updateBottomBarVisible(false)
                val topBarType = CommonTopBarTypeBuilder()
                    .setBackButtonAsNavigationButton(navigationController::navigateBack)
                    .build()
                updateTopBarType(true, topBarType)
                val toast = Toast.makeText(
                    LocalContext.current,
                    stringResource(id = R.string.settings_screen_update_profile_successfully_saved),
                    Toast.LENGTH_SHORT
                )
                LaunchedEffect(true) {
                    viewModel.initUpdateProfileState()
                }

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

        composable(route = Screen.ABOUT.route) {
            updateBottomBarVisible(true)
            val topBarType = CommonTopBarTypeBuilder()
                .setTitle("About", titleStyle = null)
                .setBackButtonAsNavigationButton(navigationController::navigateBack)
                .build()
            updateTopBarType(true, topBarType)

            AboutScreen()
        }
    }
}