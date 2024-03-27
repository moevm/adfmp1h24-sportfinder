package ru.moevm.sportfinder.domain.controller

import com.google.android.gms.maps.model.LatLng

interface WeatherController {

    fun getTemperatureForLatLng(latLng: LatLng): Int

    fun getTemperaturesForLatLng(latLngs: List<LatLng>): List<Int>
}