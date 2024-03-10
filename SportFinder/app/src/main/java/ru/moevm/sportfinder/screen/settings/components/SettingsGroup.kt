package ru.moevm.sportfinder.screen.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme

@Composable
fun SettingsGroup(
    modifier: Modifier = Modifier,
    groupTitle: String,
    withDivider: Boolean = false,
    groupContent: @Composable () -> Unit = {}
) {
    Column(modifier = modifier) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = groupTitle,
                color = SportFinderLightColorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            groupContent()
        }
        if (withDivider) {
            Divider(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingGroup() {
    SettingsGroup(groupTitle = "Common", withDivider = true, groupContent = {
        SettingNavigationItem(title = "Edit profile", navigateTo = {})
        SettingCheckboxItem(title = "Edit profile true", state = true, {})
        SettingCheckboxItem(title = "Edit profile false", state = false, {})
        SettingLabelItem(title = "Logout", isDangerous = false, action = {})
        SettingLabelItem(title = "Logout", isDangerous = true, action = {})
    })
}
