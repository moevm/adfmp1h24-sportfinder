package ru.moevm.sportfinder.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.domain.use_case.IsAutoSignInEnabledUseCase
import ru.moevm.sportfinder.domain.use_case.IsProfileExistsFromLoginScreenUseCase
import ru.moevm.sportfinder.domain.use_case.SetAutoSignInUseCase
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val isAutoSignInEnabledUseCase: IsAutoSignInEnabledUseCase,
    private val isProfileExistsFromLoginScreenUseCase: IsProfileExistsFromLoginScreenUseCase,
    private val setAutoSignInUseCase: SetAutoSignInUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AuthorizationState())
    val state = _state.asStateFlow()

    fun trySignInFromStart() {
        isAutoSignInEnabledUseCase()
            .onEach { isAuthorized ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    isAuthorized = isAuthorized,
                )
            }
            .launchIn(viewModelScope)
    }

    fun updateLogin(newLogin: String) {
        _state.value = _state.value.copy(login = newLogin)
    }

    fun updatePassword(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
    }

    fun trySignIn() {
        _state.value = _state.value.copy(isLoading = true)

        flow {
            var isAuthorized = false
            isProfileExistsFromLoginScreenUseCase(_state.value.login, _state.value.password)
                .onEach {
                    isAuthorized = it
                }
                .launchIn(viewModelScope)
                .join()
            setAutoSignInUseCase(isAuthorized)
                .launchIn(viewModelScope)
                .join()
            emit(isAuthorized)
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