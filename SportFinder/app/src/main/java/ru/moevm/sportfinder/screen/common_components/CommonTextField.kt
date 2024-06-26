package ru.moevm.sportfinder.screen.common_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme

@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    isSingleLine: Boolean = true,
    onTextChanged: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    TextField(
        value = text,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = SportFinderLightColorScheme.onPrimary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = SportFinderLightColorScheme.primary
        ),
        keyboardOptions = keyboardOptions,
        singleLine = isSingleLine,
        visualTransformation = visualTransformation,
        modifier = Modifier
            .then(modifier)
            .border(
                BorderStroke(2.dp, SportFinderLightColorScheme.primary),
                RoundedCornerShape(4.dp)
            ),
        placeholder = {
            Text(text = hint, fontSize = 16.sp)
        },
        onValueChange = onTextChanged
    )
}

@Composable
fun CommonTextFieldWithRightIcon(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    isSingleLine: Boolean = true,
    onTextChanged: (String) -> Unit,
    onIconClicked: () -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    TextField(
        value = text,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = SportFinderLightColorScheme.onPrimary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = SportFinderLightColorScheme.primary
        ),
        keyboardOptions = keyboardOptions,
        singleLine = isSingleLine,
        visualTransformation = visualTransformation,
        modifier = Modifier
            .then(modifier)
            .border(
                BorderStroke(2.dp, SportFinderLightColorScheme.primary),
                RoundedCornerShape(4.dp)
            ),
        placeholder = {
            Text(text = hint, fontSize = 16.sp)
        },
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onIconClicked),
                painter = painterResource(R.drawable.ic_running_screen_add),
                tint = SportFinderLightColorScheme.primary,
                contentDescription = null
            )
        },
        onValueChange = onTextChanged
    )
}