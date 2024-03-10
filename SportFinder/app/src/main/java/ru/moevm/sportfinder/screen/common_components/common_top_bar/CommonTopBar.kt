package ru.moevm.sportfinder.screen.common_components.common_top_bar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    type: CommonTopBarType,
) {
    val (navigationButton, titleData, menuButtons) = type
    TopAppBar(
        title = {
            titleData?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = titleData.title,
                    style = titleData.textStyle ?: LocalTextStyle.current,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        navigationIcon = {
            navigationButton?.let {
                IconButton(onClick = navigationButton.onClick) {
                    Icon(
                        painter = painterResource(id = navigationButton.iconId),
                        contentDescription = null
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = SportFinderLightColorScheme.primary,
            navigationIconContentColor = SportFinderLightColorScheme.onPrimary,
            titleContentColor = SportFinderLightColorScheme.onPrimary,
            actionIconContentColor = SportFinderLightColorScheme.onPrimary
        ),
        actions = {
            menuButtons?.let {
                Row {
                    menuButtons.forEach { button ->
                        IconButton(onClick = button.onClick) {
                            Icon(painter = painterResource(id = button.iconId), null)
                        }
                    }
                }
            }
        }
    )
}