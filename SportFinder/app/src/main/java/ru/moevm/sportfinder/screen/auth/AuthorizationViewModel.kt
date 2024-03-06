package ru.moevm.sportfinder.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(AuthorizationState())
    val state = _state.asStateFlow()

    fun updateLogin(newLogin: String) {
        _state.value = _state.value.copy(login = newLogin)
    }

    fun updatePassword(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
    }

    fun trySignIn() {
        flow { // TODO: Заменить на попытку авторизации пользователя
            _state.value = _state.value.copy(isLoading = true)
            delay(3_000)
            emit(true)
        }
            .onEach { isAuthorized ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    isAuthorized = isAuthorized,
                )
            }
            .launchIn(viewModelScope)
    }
}