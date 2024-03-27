package ru.moevm.sportfinder.screen.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.domain.use_case.UseTrainingDatabaseUseCase
import javax.inject.Inject

@HiltViewModel
class TrainingCreateViewModel @Inject constructor(
    private val useTrainingDatabaseUseCase: UseTrainingDatabaseUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(TrainingCreateState())
    val state = _state.asStateFlow()

    val availableTags = persistentListOf(
        "Тренировка", "Бег", "Асфальт", "Деревья", "Стадион"
    )

    fun onChangeNameClick() {
        _state.value = _state.value.copy(isEditNameMode = !_state.value.isEditNameMode)
    }

    fun onChangeName(newName: String) {
        _state.value = _state.value.copy(name = newName)
    }

    fun onShowSelectTagsDialogClick() {
        _state.value = _state.value.copy(isSelectTagDialogShown = true)
    }

    fun onSaveSelectedTagsClick(listOfTagIndices: List<Int>) {
        _state.value = _state.value.copy(tags = availableTags.filterIndexed { index, _ -> index in listOfTagIndices })
    }

    fun onDismissSelectTagsDialogClick() {
        _state.value = _state.value.copy(isSelectTagDialogShown = false)
    }

    fun onRemoveTagClick(index: Int) {
        _state.value = _state.value.copy(tags = _state.value.tags.toMutableList().apply { removeAt(index) })
    }

    fun onChangeDescriptionClick() {
        _state.value =
            _state.value.copy(isEditDescriptionMode = !_state.value.isEditDescriptionMode)
    }

    fun onChangeDescription(newDescription: String) {
        _state.value = _state.value.copy(description = newDescription)
    }

    fun onSaveClick(onSuccess: () -> Unit) {
        if (_state.value.name.isBlank() || _state.value.description.isBlank()) {
            return
        }
        useTrainingDatabaseUseCase.addTraining(
            _state.value.name,
            _state.value.tags,
            _state.value.description
        )
            .onEach { isSuccess ->
                if (isSuccess) {
                    onSuccess()
                }
            }
            .launchIn(viewModelScope)
    }
}