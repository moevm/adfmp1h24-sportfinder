package ru.moevm.sportfinder.domain.sharing

import javax.inject.Inject
import kotlin.math.abs

class SharingTextGeneratorImpl @Inject constructor() : SharingTextGenerator {
    override fun fromSportCourtInfo(
        name: String,
        address: String,
        tags: List<String>,
        info: String
    ): String {
        if (name.isBlank()) {
            return notEnoughData()
        }
        return buildString {
            append("Давай позанимаемся вместе! Нашёл площадку: \"$name\".\n")
            if (address.isNotBlank()) {
                append("Её адрес: \"$address\".\n")
            }
            if (tags.any { it.isNotBlank() }) {
                append("Её особенности: ${tags.filter { it.isNotBlank() }.joinToString(separator = ", ")}.\n")
            }
            if (info.isNotBlank()) {
                append("Подробней о ней:\n$info\n")
            }
        }
    }

    override fun fromRunningInfo(name: String, distance: Double, tags: List<String>): String {
        if (name.isBlank()) {
            return notEnoughData()
        }
        return buildString {
            append("Давай побегаем вместе! Нашёл маршрут: \"$name\".\n")
            if (abs(distance) > 0.01) {
                append("Его дистанция ${"%.2f".format(distance)}м.\n")
            }
            if (tags.any { it.isNotBlank() }) {
                append("Его особенности: ${tags.joinToString(separator = ", ")}.\n")
            }
        }
    }

    override fun fromTrainingInfo(name: String, tags: List<String>): String {
        if (name.isBlank()) {
            return notEnoughData()
        }
        return buildString {
            append("Давай потренируемся вместе! Нашёл тренировку: \"$name\".\n")
            if (tags.any { it.isNotBlank() }) {
                append("Её особенности: ${tags.joinToString(separator = ", ")}.\n")
            }
        }
    }

    private fun notEnoughData(): String {
        return "Ошибка получения данных :("
    }
}