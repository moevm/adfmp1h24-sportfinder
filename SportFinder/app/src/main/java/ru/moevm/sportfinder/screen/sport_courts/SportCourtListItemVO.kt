package ru.moevm.sportfinder.screen.sport_courts

import androidx.annotation.DrawableRes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SportCourtListItemVO(
    val courtId: Long,
    val name: String,
    val tags: ImmutableList<String> = persistentListOf(),
    val distance: Float? = null,
    val temperature: Float? = null,
    @DrawableRes val imagePlaceholder: Int,
)
