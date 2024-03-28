package ru.moevm.sportfinder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import ru.moevm.sportfinder.data.dto.RunningDTO
import ru.moevm.sportfinder.data.dto.TrainingDTO
import ru.moevm.sportfinder.domain.use_case.IsFirstStartUseCase
import ru.moevm.sportfinder.domain.use_case.UseRunningDatabaseUseCase
import ru.moevm.sportfinder.domain.use_case.UseTrainingDatabaseUseCase
import ru.moevm.sportfinder.screen.common_components.common_top_bar.CommonTopBarType
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val isFirstStartUseCase: IsFirstStartUseCase,
    private val useTrainingDatabaseUseCase: UseTrainingDatabaseUseCase,
    private val useRunningDatabaseUseCase: UseRunningDatabaseUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainActivityState())
    val state = _state.asStateFlow()

    fun checkIsFirstStart() {
        flow<Unit> {
            val isFirstStart = isFirstStartUseCase().first()
            Log.d("mylog", "isFirstStart = $isFirstStart")
            if (!isFirstStart) {
                return@flow
            }

            val fakeRunnings = getFakeRunnings()
            fakeRunnings.forEach { running ->
                useRunningDatabaseUseCase.addRunning(
                    title = running.title,
                    tags = running.tags,
                    points = running.points,
                    author = running.author
                ).launchIn(viewModelScope).join()
            }
            val fakeTraining = getFakeTrainings()
            fakeTraining.forEach { training ->
                useTrainingDatabaseUseCase.addTraining(
                    name = training.name,
                    tags = training.tags,
                    description = training.description,
                    author = training.author
                ).launchIn(viewModelScope).join()
            }

        }.launchIn(viewModelScope)
    }

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

    private fun getFakeRunnings() : List<RunningDTO> {
        return listOf(
            RunningDTO(
                author="admin",
                title="Крутая пробежка по Питеру!",
                tags = listOf("Асфальт", "Достопримечательности"),
                dist=1448.0714572497864,
                points = listOf(
                    LatLng(59.94096865417641,30.324662514030933),
                    LatLng(59.93587691339466,30.322776921093464),
                    LatLng(59.93481335516561,30.331262424588203),
                    LatLng(59.93656437847954,30.331605076789856),
                    LatLng(59.93641405444622,30.334990695118904),
                )
            ),
            RunningDTO(
                author="admin",
                title="Летний парк",
                tags = listOf("Деревья", "Бездорожье", "Чистый воздух"),
                dist=954.606871149625,
                points = listOf(
                    LatLng(59.94477724947736,30.3288209438324),
                    LatLng(59.94325990917501,30.330064818263054),
                    LatLng(59.944262745230475,30.330004133284092),
                    LatLng(59.943796258956446,30.331360995769504),
                    LatLng(59.943947221215595,30.331955440342426),
                    LatLng(59.94472888910483,30.33201813697815),
                    LatLng(59.94407349835044,30.33336494117975),
                    LatLng(59.9423648540576,30.334531031548977),
                    LatLng(59.942219594240974,30.33350944519043),
                    LatLng(59.94194653764928,30.3348458558321),
                )
            ),
        )
    }

    private fun getFakeTrainings(): List<TrainingDTO> {
        return listOf(
            TrainingDTO(
                name = "MyCoolTrain",
                author="admin",
                tags = listOf("Асфальт", "Деревья"),
                description = """
1. Размять шею
2. Размять руки и ноги
3. Прыжки на скакалке 3 минуты
4. Отжимания 30 раз
5. Отдых 3 минуты
                """.trimIndent()
            ),
            TrainingDTO(
                name = "Я самый сильный",
                author = "admin",
                tags = listOf("Тренировка", "Асфальт", "Стадион"),
                description = """
1. Размять руки и ноги
2. Сделать 20 отжиманий
3. Сделать 40 приседаний
4. Перерыв 5 минут
5. Пробежать 500м
6. Сделать фото на память!
                """.trimIndent()
            )
        )
    }
}