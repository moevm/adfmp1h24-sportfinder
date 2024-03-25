package ru.moevm.sportfinder.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.screen.settings.components.SettingLabelItem
import ru.moevm.sportfinder.screen.settings.components.SettingNavigationItem
import ru.moevm.sportfinder.screen.settings.components.SettingsGroup

@Composable
fun MainSettingsScreen(
    onLogOutClick: () -> Unit,
    navigateToUpdateProfile: () -> Unit,
    navigateToAboutScreen:  () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        item {
            SettingsGroup(groupTitle = stringResource(id = R.string.settings_screen_common_group_title)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SettingNavigationItem(
                        title = stringResource(id = R.string.settings_screen_common_update_profile_title),
                        navigateTo = navigateToUpdateProfile
                    )
                    SettingNavigationItem(
                        title = "About",
                        navigateTo = navigateToAboutScreen
                    )
                    SettingLabelItem(
                        title = stringResource(id = R.string.settings_screen_common_logout_title),
                        isDangerous = true,
                        action = onLogOutClick
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSettingScreen() {
    MainSettingsScreen(
        onLogOutClick = {},
        navigateToUpdateProfile = {},
        navigateToAboutScreen = {  }
    )
}