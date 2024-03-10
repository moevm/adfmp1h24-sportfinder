package ru.moevm.sportfinder.screen.common_components.common_top_bar

import kotlinx.collections.immutable.ImmutableList

data class CommonTopBarType(
    val navigationButton: CommonTopBarButtonData? = null,
    val title: CommonTopBarTitleData? = null,
    val menuButtons: ImmutableList<CommonTopBarButtonData>? = null,
)
