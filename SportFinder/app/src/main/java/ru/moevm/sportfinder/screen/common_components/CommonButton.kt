package ru.moevm.sportfinder.screen.common_components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = SportFinderLightColorScheme.primary
        )
    ) {
        content()
    }
}

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = SportFinderLightColorScheme.primary
        )
    ) {
        Text(
            text = buttonText,
            color = SportFinderLightColorScheme.onPrimary
        )
    }
}