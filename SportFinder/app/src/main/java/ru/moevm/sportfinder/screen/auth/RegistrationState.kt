package ru.moevm.sportfinder.screen.auth

data class RegistrationState(
    val login: String = "",
    val password: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
