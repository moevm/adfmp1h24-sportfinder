package ru.moevm.sportfinder.screen.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme

@Composable
fun SettingLabelItem(
    title: String,
    isDangerous: Boolean,
    action: () -> Unit = {},
) {
    Box {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .clickable(onClick = action),
            color = if (isDangerous) SportFinderLightColorScheme.onError
            else SportFinderLightColorScheme.onSurface,
            text = title
        )
    }
}

@Preview
@Composable
fun PreviewSettingLabelItem() {
    SettingLabelItem(title = "Edit profile", isDangerous = true, action = {})
}