package ru.moevm.sportfinder.screen.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.domain.sharing.SharingTextGenerator
import javax.inject.Inject

@HiltViewModel
class TrainingInfoViewModel @Inject constructor(
    private val sharingTextGenerator: SharingTextGenerator,
) : ViewModel() {

    private val _state = MutableStateFlow(TrainingInfoState())
    val state = _state.asStateFlow()

    fun getSharingText(): String {
        return sharingTextGenerator.fromTrainingInfo(
            _state.value.name,
            _state.value.tags
        )
    }

    fun updateTrainingInfoState() {
        flow {
            val fakeData = getFakeData()
            emit(fakeData)
        }
            .flowOn(Dispatchers.IO)
            .onEach { newData ->
                _state.value = newData
            }
            .launchIn(viewModelScope)
    }

    private fun getFakeData() = TrainingInfoState(
        name = "Скакалка",
        tags = persistentListOf("Тренировка", "Асфальт", "Деревья"),
        description = "1 шаг: Провести разминку - вращение головой, вращение руками, вращение туловищем\n" +
                "2 шаг: Взять скакалку, выполнить 80 прыжков, сделать 3 подхода с уменьшением количества прыжков каждый раз на 10\n"
    )

}