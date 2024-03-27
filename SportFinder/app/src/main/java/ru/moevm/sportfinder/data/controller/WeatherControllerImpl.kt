package ru.moevm.sportfinder.data.controller

import com.google.android.gms.maps.model.LatLng
import ru.moevm.sportfinder.domain.controller.WeatherController
import javax.inject.Inject

private const val START_TEMPERATURE = 5

class WeatherControllerImpl @Inject constructor(): WeatherController {
    override fun getTemperatureForLatLng(latLng: LatLng): Int {
        return (START_TEMPERATURE + Math.random() * 4).toInt()
    }

    override fun getTemperaturesForLatLng(latLngs: List<LatLng>): List<Int> {
        return latLngs.map { getTemperatureForLatLng(it) }
    }
}