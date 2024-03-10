package ru.moevm.sportfinder.screen.common_components.common_top_bar

import androidx.annotation.DrawableRes

data class CommonTopBarButtonData(
    @DrawableRes val iconId: Int,
    val onClick: () -> Unit,
)
