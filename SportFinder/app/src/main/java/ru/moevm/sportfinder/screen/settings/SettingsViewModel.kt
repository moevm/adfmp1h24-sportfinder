package ru.moevm.sportfinder.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _updateProfileState = MutableStateFlow(UpdateProfileState())
    val updateProfileState = _updateProfileState.asStateFlow()

    fun initUpdateProfileState() {
        _updateProfileState.value = _updateProfileState.value.copy(
            name = "Name Placeholder"
        )
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
        /* do nothing */
    }

    fun onLogOutClicked(onSuccessLogOut: () -> Unit) {
        flow {
            emit(true)
        }
            .onEach { isLogOutSuccessful ->
                if (isLogOutSuccessful) {
                    onSuccessLogOut()
                }
            }
            .launchIn(viewModelScope)
    }
}