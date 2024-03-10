package ru.moevm.sportfinder.screen.navigation

enum class Screen(val route: String) {
    AUTH_SCREEN("auth_screen"),
    REG_SCREEN("reg_screen"),
    PROFILE_SCREEN("profile_screen"),
    SPORT_COURT_MAP_SCREEN("sport_court_map_screen"),
    SPORT_COURT_LIST_SCREEN("sport_court_list_screen"),
}

enum class ScreensSubgraphs(val route: String) {
    AUTH("auth"),
    PROFILE("profile"),
    SPORT_COURT("sport_court"),
}