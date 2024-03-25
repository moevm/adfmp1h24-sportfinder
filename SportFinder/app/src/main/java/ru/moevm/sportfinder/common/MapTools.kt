package ru.moevm.sportfinder.common

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlin.math.abs

object MapTools {
    private const val pointEps = 1e-4

    fun calcDistance(points: List<LatLng>): Double {
        var dist = 0.0
        for (i in 1 until points.size) {
            dist += SphericalUtil.computeDistanceBetween(points[i-1], points[i])
        }
        return dist
    }

    fun findPointInList(
        currPoint: LatLng,
        listOfPoints: List<LatLng>
    ): List<LatLng> {
        val answer = mutableListOf<LatLng>()
        listOfPoints.forEach { point ->
            if (abs(currPoint.latitude - point.latitude) < pointEps &&
                abs(currPoint.longitude - point.longitude) < pointEps) {
                answer.add(point)
            }
        }
        return answer
    }
}