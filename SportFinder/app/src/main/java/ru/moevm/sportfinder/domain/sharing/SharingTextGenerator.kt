package ru.moevm.sportfinder.domain.sharing

interface SharingTextGenerator {

    fun fromSportCourtInfo(name: String, address: String, tags: List<String>, info: String): String

    fun fromRunningInfo(name: String, distance: Double, tags: List<String>): String

    fun fromTrainingInfo(name: String, tags: List<String>): String
}