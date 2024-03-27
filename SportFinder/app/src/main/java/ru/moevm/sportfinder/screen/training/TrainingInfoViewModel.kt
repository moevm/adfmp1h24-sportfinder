package ru.moevm.sportfinder.screen.training

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.domain.sharing.SharingTextGenerator
import ru.moevm.sportfinder.domain.use_case.UseTrainingDatabaseUseCase
import javax.inject.Inject

@HiltViewModel
class TrainingInfoViewModel @Inject constructor(
    private val sharingTextGenerator: SharingTextGenerator,
    private val useTrainingDatabaseUseCase: UseTrainingDatabaseUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val trainingId = savedStateHandle.get<Int>("trainingId")

    private val _state = MutableStateFlow(TrainingInfoState())
    val state = _state.asStateFlow()

    fun getSharingText(): String {
        return sharingTextGenerator.fromTrainingInfo(
            _state.value.name,
            _state.value.tags
        )
    }

    fun updateTrainingInfoState() {
        trainingId?.let {
            useTrainingDatabaseUseCase.getTrainingById(trainingId)
                .flowOn(Dispatchers.IO)
                .onEach { trainingDTO ->
                    if (trainingDTO == null) {
                        return@onEach
                    }
                    _state.value = TrainingInfoState(
                        name = trainingDTO.name,
                        tags = trainingDTO.tags.toPersistentList(),
                        description = trainingDTO.description
                    )
                }
                .launchIn(viewModelScope)
        }
    }
}