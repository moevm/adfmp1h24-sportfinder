package ru.moevm.sportfinder.screen.running

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.common.Constants
import ru.moevm.sportfinder.common.MapTools
import javax.inject.Inject

@HiltViewModel
class RunningCreateViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(RunningCreateState())
    val state = _state.asStateFlow()

    val startPoint = Constants.SPB_CENTER_POINT

    init {
        getTags()
            .onEach { result ->
                _state.value = _state.value.copy(availableTags = result)
        }.launchIn(viewModelScope)
    }

    private fun getTags() = flow {
        emit(persistentListOf("Деревья", "Бездорожье", "Асфальт"))
    }

    fun onTitleChanged(newTitle: String) {
        _state.value = _state.value.copy(title = newTitle)
    }

    fun onShowSelectTagsDialogClick() {
        _state.value = _state.value.copy(isSelectTagDialogShown = true)
    }

    fun onSaveTagsDialogClick(listOfTagIndices: List<Int>) {
        _state.value = _state.value.copy(
            listOfTags = _state.value.availableTags.filterIndexed { index, _ ->
                index in listOfTagIndices
            }.toMutableList()
        )
    }

    fun onRemoveTagClick(index: Int) {
        _state.value = _state.value.copy(
            listOfTags = _state.value.listOfTags.toMutableList().apply { removeAt(index) }
        )
    }

    fun onDismissTagsDialogClick() {
        _state.value = _state.value.copy(isSelectTagDialogShown = false)
    }

    fun addPoint(point: LatLng) {
        val matchedPoints =
            MapTools.findPointInList(point, _state.value.listOfPoints)
        if (matchedPoints.isEmpty()) {
            val newPoints = _state.value.listOfPoints.toMutableList().apply { add(point) }
            _state.value = _state.value.copy(
                listOfPoints = newPoints,
                distance = MapTools.calcDistance(newPoints)
            )
        }
    }

    fun removePoint(point: LatLng) {
        val matchedPoints =
            MapTools.findPointInList(point, _state.value.listOfPoints)
        if (matchedPoints.isNotEmpty()) {
            val newPoints = _state.value.listOfPoints.toMutableList().apply { remove(matchedPoints.first()) }
            _state.value = _state.value.copy(
                listOfPoints = newPoints,
                distance = MapTools.calcDistance(newPoints)
            )
        }
    }
}