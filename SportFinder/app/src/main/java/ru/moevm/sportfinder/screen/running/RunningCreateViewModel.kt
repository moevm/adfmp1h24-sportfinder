package ru.moevm.sportfinder.screen.running

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.common.Constants
import ru.moevm.sportfinder.common.MapTools
import ru.moevm.sportfinder.domain.use_case.UseRunningDatabaseUseCase
import javax.inject.Inject

@HiltViewModel
class RunningCreateViewModel @Inject constructor(
    private val useRunningDatabaseUseCase: UseRunningDatabaseUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(RunningCreateState())
    val state = _state.asStateFlow()

    val startPoint = Constants.SPB_CENTER_POINT
    val tags = listOf("Деревья", "Бездорожье", "Асфальт", "Достопримечательности", "Чистый воздух")
        .sorted()
        .toPersistentList()

    fun onTitleChanged(newTitle: String) {
        _state.value = _state.value.copy(title = newTitle)
    }

    fun onCustomTagChanged(newCustomTag: String) {
        _state.value = _state.value.copy(customTag = newCustomTag.filter {
            it.isLetterOrDigit() || it.isWhitespace()
        })
    }

    fun onCustomTagAdded() {
        val newTag = _state.value.customTag
        if (newTag.isNotBlank() && newTag !in _state.value.listOfTags) {
            _state.value = _state.value.copy(
                customTag = "",
                listOfTags = _state.value.listOfTags.toMutableList().apply {
                    add(newTag)
                }.sorted()
            )
        }
    }

    fun onShowSelectTagsDialogClick() {
        _state.value = _state.value.copy(
            initialSelectedTagIndices = tags.foldIndexed(mutableListOf<Int>()) { index, acc, tag ->
                if (tag in _state.value.listOfTags) {
                    acc.add(index)
                }
                acc
            }.toPersistentList(),
            availableTags = tags,
            isSelectTagDialogShown = true
        )
    }

    fun onSaveTagsDialogClick(listOfTagIndices: List<Int>) {
        _state.value = _state.value.copy(
            listOfTags = _state.value.listOfTags.toMutableList().apply {
                tags.forEachIndexed { index, s ->
                    if (index in listOfTagIndices) {
                        if (s !in _state.value.listOfTags) {
                            add(s)
                        }
                    } else {
                        remove(s)
                    }
                }
            }.sorted()
        )
    }

    fun onRemoveTagClick(index: Int) {
        _state.value = _state.value.copy(
            listOfTags = _state.value.listOfTags.toMutableList().apply { removeAt(index) }.sorted()
        )
    }

    fun onDismissTagsDialogClick() {
        _state.value = _state.value.copy(isSelectTagDialogShown = false)
    }

    fun onSaveClick(onSuccess: () -> Unit) {
        if (_state.value.title.isBlank() || _state.value.listOfPoints.isEmpty()) {
            return
        }
        useRunningDatabaseUseCase.addRunning(
            _state.value.title,
            _state.value.listOfTags,
            _state.value.listOfPoints
        )
            .onEach { isAdded ->
                if (isAdded) {
                    onSuccess()
                }
            }
            .launchIn(viewModelScope)
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