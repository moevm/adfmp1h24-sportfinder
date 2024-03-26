package ru.moevm.sportfinder.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.domain.use_case.GetProfileImageUrlUseCase
import ru.moevm.sportfinder.domain.use_case.GetProfileNameUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileNameUseCase: GetProfileNameUseCase,
    private val getProfileImageUrlUseCase: GetProfileImageUrlUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    fun updateProfileData() {
        combine(
            getProfileNameUseCase(),
            getProfileImageUrlUseCase()
        ) { name, imageUrl ->
            (name ?: "") to (imageUrl ?: "")
        }
            .onEach { (name, imageUrl) ->
                _state.value = _state.value.copy(
                    profileName = name,
                    profileImageUrl = imageUrl
                )
            }
            .launchIn(viewModelScope)
    }

    fun switchTab(newTabIndex: Int) {
        _state.value = _state.value.copy(
            tabIndex = newTabIndex
        )
    }
}