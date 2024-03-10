package ru.moevm.sportfinder.screen.settings.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme

@Composable
fun SettingCheckboxItem(
    title: String,
    state: Boolean,
    updateCheckState: (Boolean) -> Unit
) {
    Row {
        Box(
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                text = title
            )
        }
        Switch(
            checked = state,
            onCheckedChange = {
                updateCheckState(it)
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = SportFinderLightColorScheme.primary,
                checkedThumbColor = SportFinderLightColorScheme.secondary
            )
        )
    }
}

@Preview
@Composable
fun PreviewSettingCheckboxItem() {
    SettingCheckboxItem(title = "Edit profile", true, {})
}
