package ru.moevm.sportfinder.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    fun updateLogin(newLogin: String) {
        _state.value = _state.value.copy(login = newLogin)
    }

    fun updatePassword(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
    }

    fun trySignUp() {
        flow { // TODO: Заменить на попытку регистрации пользователя
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