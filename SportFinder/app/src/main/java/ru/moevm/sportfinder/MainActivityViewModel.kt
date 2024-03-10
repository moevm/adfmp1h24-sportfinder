package ru.moevm.sportfinder

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.moevm.sportfinder.screen.common_components.common_top_bar.CommonTopBarType
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MainActivityState())
    val state = _state.asStateFlow()

    fun onBottomBarNewItemSelect(newItem: Int) {
        _state.value = _state.value.copy(
            bottomBarSelectedItem = newItem
        )
    }

    fun onBottomBarVisibleStateUpdate(isVisible: Boolean) {
        _state.value = _state.value.copy(
            isSupportedBottomBar = isVisible
        )
    }

    fun onTopBarTypeUpdated(isVisible: Boolean, type: CommonTopBarType?) {
        _state.value = _state.value.copy(
            isSupportedTopBar = isVisible,
            topBarType = type ?: CommonTopBarType(),
        )
    }
}