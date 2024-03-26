package ru.moevm.sportfinder.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.domain.use_case.GetProfileImageUrlUseCase
import ru.moevm.sportfinder.domain.use_case.GetProfileNameUseCase
import ru.moevm.sportfinder.domain.use_case.SetAutoSignInUseCase
import ru.moevm.sportfinder.domain.use_case.SetProfileDataUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getProfileNameUseCase: GetProfileNameUseCase,
    private val getProfileImageUrlUseCase: GetProfileImageUrlUseCase,
    private val setProfileDataUseCase: SetProfileDataUseCase,
    private val setAutoSignInUseCase: SetAutoSignInUseCase,
) : ViewModel() {

    private val _updateProfileState = MutableStateFlow(UpdateProfileState())
    val updateProfileState = _updateProfileState.asStateFlow()

    fun initUpdateProfileState() {
        combine(
            getProfileNameUseCase(),
            getProfileImageUrlUseCase()
        ) { name, imageUrl ->
            (name ?: "") to (imageUrl ?: "")
        }
            .onEach { (name, imageUrl) ->
                _updateProfileState.value = _updateProfileState.value.copy(
                    name = name,
                    imageUrl = imageUrl
                )
            }
            .launchIn(viewModelScope)
    }

    fun onImageUrlEntered(newUrl: String) {
        _updateProfileState.value = _updateProfileState.value.copy(
            imageUrl = newUrl
        )
    }

    fun onNameEntered(newName: String) {
        _updateProfileState.value = _updateProfileState.value.copy(
            name = newName
        )
    }

    fun onSaveClicked() {
        setProfileDataUseCase(_updateProfileState.value.name, _updateProfileState.value.imageUrl)
            .launchIn(viewModelScope)
    }

    fun onLogOutClicked(onSuccessLogOut: () -> Unit) {
        setAutoSignInUseCase(false)
            .onEach { isLogOutSuccessful ->
                if (isLogOutSuccessful) {
                    onSuccessLogOut()
                }
            }
            .launchIn(viewModelScope)
    }
}