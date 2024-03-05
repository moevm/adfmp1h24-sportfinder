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
}