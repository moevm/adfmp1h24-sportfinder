package ru.moevm.sportfinder.domain.use_case

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.moevm.sportfinder.domain.controller.WeatherController
import javax.inject.Inject

class GetWeatherTemperatureUseCase @Inject constructor(
    private val weatherController: WeatherController
) {

    operator fun invoke(latLng: LatLng): Flow<Int> = flow {
        val temperature = weatherController.getTemperatureForLatLng(latLng)

        emit(temperature)
    }
}