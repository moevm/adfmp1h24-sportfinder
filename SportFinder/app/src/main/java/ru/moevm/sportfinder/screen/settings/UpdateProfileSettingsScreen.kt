package ru.moevm.sportfinder.screen.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.screen.common_components.CommonButton
import ru.moevm.sportfinder.screen.common_components.CommonTextField

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UpdateProfileSettingsScreen(
    state: UpdateProfileState,
    onImageUrlEntered: (String) -> Unit,
    onNameEntered: (String) -> Unit,
    onSaveClicked: () -> Unit,
) {
    val (imageUrl, name) = state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 8.dp)
                .overscroll(ScrollableDefaults.overscrollEffect())
                .verticalScroll(rememberScrollState(), enabled = true),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextFieldWithLabel(
                labelTextId = R.string.settings_screen_update_profile_change_name,
                textFieldHint = R.string.settings_screen_update_profile_change_name_hint,
                textFieldValue = name,
                onNameEntered
            )

            TextFieldWithLabel(
                labelTextId = R.string.settings_screen_update_profile_change_image_url,
                textFieldHint = R.string.settings_screen_update_profile_change_image_url_hint,
                textFieldValue = imageUrl,
                onImageUrlEntered
            )
        }

        CommonButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSaveClicked,
            buttonText = stringResource(id = R.string.settings_screen_update_profile_save_button_title).uppercase()
        )
    }
}

@Composable
fun TextFieldWithLabel(
    @StringRes labelTextId: Int,
    @StringRes textFieldHint: Int,
    textFieldValue: String,
    onTextEntered: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = labelTextId),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
        )
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            text = textFieldValue,
            hint = stringResource(id = textFieldHint),
            onTextChanged = onTextEntered
        )
    }
}