package ru.moevm.sportfinder

import ru.moevm.sportfinder.screen.common_components.common_top_bar.CommonTopBarType

data class MainActivityState(
    val isSupportedBottomBar: Boolean = false,
    val bottomBarSelectedItem: Int = 0,
    val isSupportedTopBar: Boolean = false,
    val topBarType: CommonTopBarType = CommonTopBarType(),
)
