package ru.moevm.sportfinder.screen.common_components

import androidx.annotation.DrawableRes

data class BottomNavItem(
    @DrawableRes val iconId: Int,
    val onItemClick: () -> Unit,
)
