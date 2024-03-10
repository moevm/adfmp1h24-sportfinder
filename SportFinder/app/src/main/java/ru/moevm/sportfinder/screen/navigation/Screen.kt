package ru.moevm.sportfinder.screen.navigation

enum class Screen(val route: String) {
    AUTH_SCREEN("auth_screen"),
    REG_SCREEN("reg_screen"),
    PROFILE_SCREEN("profile_screen"),
    SPORT_COURT_MAP_SCREEN("sport_court_map_screen"),
    SPORT_COURT_LIST_SCREEN("sport_court_list_screen"),
    SETTINGS_MAIN_SCREEN("settings_main_screen"),
    SETTINGS_UPDATE_PROFILE_SCREEN("settings_update_profile_screen")
}

enum class ScreensSubgraphs(val route: String) {
    AUTH("auth"),
    PROFILE("profile"),
    SPORT_COURT("sport_court"),
    SETTINGS("settings")
}