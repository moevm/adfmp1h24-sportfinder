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
import ru.moevm.sportfinder.common.MapTools
import javax.inject.Inject

@HiltViewModel
class RunningInfoViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(RunningInfoState())
    val state = _state.asStateFlow()

    init {
        initState()
    }

    private fun initState() {
        getRunningInfo()
            .onEach { result ->
                _state.value = result
            }
            .launchIn(viewModelScope)
    }

    private fun getRunningInfo() = flow {
        val listOfPoints = persistentListOf(
            LatLng(59.935748421515974, 30.322072841227058),
                    LatLng(59.93502684218911, 30.329101569950584),
                    LatLng(59.93499274482597, 30.33071760088205),
                    LatLng(59.93663912034306, 30.33155545592308),
                    LatLng(59.936875437890905, 30.331552103161812),
                    LatLng(59.93688837064344, 30.33122722059488),
                    LatLng(59.937286596178, 30.330968387424946),
                    LatLng(59.93770010071924, 30.33155545592308),
                    LatLng(59.93770379570002, 30.331888049840927),
                    LatLng(59.93790567538793, 30.33197656273842),
                    LatLng(59.93761125310282, 30.33311516046524),
                    LatLng(59.937393415879164, 30.336493402719498),
                    LatLng(59.93902925951585, 30.336864553391933),
                    LatLng(59.93936128897312, 30.336656011641022),
                    LatLng(59.94003826943836, 30.340010449290276),
        )
        emit(
            RunningInfoState(
                title = "Асфальт и парк",
                listOfPoints = listOfPoints,
                distance = MapTools.calcDistance(listOfPoints),
                listOfTags = persistentListOf("Набережная", "Асфальт", "Перекус", "Деревья")
            )
        )
    }
}