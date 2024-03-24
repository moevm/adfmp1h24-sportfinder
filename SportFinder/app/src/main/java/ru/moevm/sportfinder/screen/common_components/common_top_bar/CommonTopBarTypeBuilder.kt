package ru.moevm.sportfinder.screen.common_components.common_top_bar

import androidx.annotation.DrawableRes
import androidx.compose.ui.text.TextStyle
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import ru.moevm.sportfinder.R

class CommonTopBarTypeBuilder {
    private var navigationButton: CommonTopBarButtonData? = null
    private var titleData: CommonTopBarTitleData? = null
    private var menuButtons: MutableList<CommonTopBarButtonData>? = null

    fun setNavigationButton(@DrawableRes iconId: Int, action: () -> Unit): CommonTopBarTypeBuilder {
        navigationButton = CommonTopBarButtonData(iconId, action)
        return this
    }

    fun setBackButtonAsNavigationButton(onBackClicked: () -> Unit): CommonTopBarTypeBuilder {
        navigationButton = CommonTopBarButtonData(R.drawable.ic_top_bar_back_arrow, onBackClicked)
        return this
    }

    fun setTitle(newTitle: String, titleStyle: TextStyle?): CommonTopBarTypeBuilder {
        titleData = CommonTopBarTitleData(newTitle, titleStyle)
        return this
    }

    fun addMenuButton(@DrawableRes iconId: Int, action: () -> Unit): CommonTopBarTypeBuilder {
        if ((menuButtons?.size ?: 0) >= 3) {
            throw IllegalStateException("Too many menu buttons for CommonTopBar")
        }
        if (menuButtons == null) {
            menuButtons = mutableListOf()
        }
        requireNotNull(menuButtons).add(CommonTopBarButtonData(iconId, action))
        return this
    }

    fun build(): CommonTopBarType {
        return CommonTopBarType(
            navigationButton = navigationButton,
            title = titleData,
            menuButtons = menuButtons?.toPersistentList() ?: persistentListOf()
        )
    }
}