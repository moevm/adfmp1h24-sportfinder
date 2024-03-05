package ru.moevm.sportfinder.screen.navigation

enum class Screen(val route: String) {
    AUTH_SCREEN("auth_screen"),
    REG_SCREEN("reg_screen"),
}

enum class ScreensSubgraphs(val route: String) {
    AUTH("auth"),
}