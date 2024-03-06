package ru.moevm.sportfinder.screen.profile

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
class ProfileViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        updateProfileState()
    }

    fun switchTab(newTabIndex: Int) {
        _state.value = _state.value.copy(
            tabIndex = newTabIndex
        )
    }

    private fun updateProfileState() {
        flow {
            emit("Name Placeholder")
        }.onEach { name ->
            _state.value = _state.value.copy(
                profileName = name
            )
        }.launchIn(viewModelScope)
    }
}