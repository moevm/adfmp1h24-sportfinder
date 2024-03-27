package ru.moevm.sportfinder.domain.use_case

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.moevm.sportfinder.domain.controller.WeatherController
import javax.inject.Inject

class GetWeatherTemperaturesUseCase @Inject constructor(
    private val weatherController: WeatherController
) {

    operator fun invoke(latLngs: List<LatLng>): Flow<List<Int>> = flow {
        val temperatures = weatherController.getTemperaturesForLatLng(latLngs)

        emit(temperatures)
    }
}