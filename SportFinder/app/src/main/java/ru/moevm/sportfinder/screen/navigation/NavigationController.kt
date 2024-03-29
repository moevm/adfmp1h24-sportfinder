package ru.moevm.sportfinder.screen.navigation

import androidx.navigation.NavHostController

class NavigationController(
    val navHostController: NavHostController
) {

    fun navigateBack() =
        navHostController.popBackStack()

    fun navigateToRegistrationScreen() =
        navHostController.navigate(Screen.REG_SCREEN.route)

    fun navigateToAuthScreen() =
        navHostController.navigate(Screen.AUTH_SCREEN.route)

    fun navigateFromAuthToProfile() =
        navHostController.navigate(ScreensSubgraphs.PROFILE.route) {
            popUpTo(ScreensSubgraphs.AUTH.route) {
                inclusive = true
            }
        }

    fun navigateToProfile() =
        navHostController.navigate(Screen.PROFILE_SCREEN.route)

    fun navigateToSportCourt() =
        navHostController.navigate(ScreensSubgraphs.SPORT_COURT.route)

    fun navigateToSportCourtMap() =
        navHostController.navigate(Screen.SPORT_COURT_MAP_SCREEN.route)

    fun navigateToSportCourtList() =
        navHostController.navigate(Screen.SPORT_COURT_LIST_SCREEN.route)

    fun navigateToSportCourtInfo(id: Int) =
        navHostController.navigate(Screen.SPORT_COURT_INFO_SCREEN.route + "/$id")

    fun navigateToSettings() =
        navHostController.navigate(ScreensSubgraphs.SETTINGS.route)

    fun navigateToSettingsUpdateProfile() =
        navHostController.navigate(Screen.SETTINGS_UPDATE_PROFILE_SCREEN.route)

    fun navigateToTraining() =
        navHostController.navigate(ScreensSubgraphs.TRAINING.route)

    fun navigateToTrainingList() =
        navHostController.navigate(Screen.TRAINING_LIST_SCREEN.route)

    fun navigateToTrainingInfo(id: Int) =
        navHostController.navigate(Screen.TRAINING_INFO_SCREEN.route + "/$id")

    fun navigateToTrainingCreate() =
        navHostController.navigate(Screen.TRAINING_CREATE_SCREEN.route)
        
    fun navigateToRunning() =
        navHostController.navigate(ScreensSubgraphs.RUNNING.route)

    fun navigateToRunningList() =
        navHostController.navigate(Screen.RUNNING_LIST_SCREEN.route)

    fun navigateToRunningCreate() =
        navHostController.navigate(Screen.RUNNING_CREATE_SCREEN.route)

    fun navigateToRunningInfo(id: Int) =
        navHostController.navigate(Screen.RUNNING_INFO_SCREEN.route + "/$id")

    fun navigateToAboutScreen() =
        navHostController.navigate(Screen.ABOUT.route)
}