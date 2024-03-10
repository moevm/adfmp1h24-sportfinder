package ru.moevm.sportfinder.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.screen.common_components.CommonButton
import ru.moevm.sportfinder.screen.common_components.CommonTextField
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrationScreen(
    state: RegistrationState,
    onLoginEnter: (String) -> Unit,
    onPasswordEnter: (String) -> Unit,
    onSingUpClicked: () -> Unit,
    onNavigateToAuthClicked: () -> Unit,
    onNavigateToProfileScreen: () -> Unit,
) {
    val (login, password, errorMessage, isLoading, isAuthorized) = state

    LaunchedEffect(isAuthorized) {
        if (isAuthorized) {
            onNavigateToProfileScreen()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.sportfinder_logo),
                contentDescription = "logo",
                alignment = Alignment.Center,
            )
            val (loginFieldFocus, passwordFieldFocus) = remember { FocusRequester.createRefs() }

            CommonTextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(0.8f)
                    .focusRequester(loginFieldFocus)
                    .focusProperties { next = passwordFieldFocus },
                text = login,
                hint = stringResource(id = R.string.reg_screen_login_title).uppercase(),
                onTextChanged = onLoginEnter,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            )

            CommonTextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(0.8f)
                    .focusRequester(passwordFieldFocus),
                text = password,
                hint = stringResource(id = R.string.reg_screen_password_title).uppercase(),
                onTextChanged = onPasswordEnter,
                visualTransformation = PasswordVisualTransformation()
            )

            CommonButton(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = onSingUpClicked
            ) {
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.Center),
                            color = SportFinderLightColorScheme.onPrimary
                        )
                    }
                } else
                    Text(
                        text = stringResource(id = R.string.reg_screen_submit_button).uppercase(),
                        color = SportFinderLightColorScheme.onPrimary
                    )
            }

            Text(
                text = stringResource(id = R.string.reg_screen_nav_to_auth_button).uppercase(),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clickable(onClick = onNavigateToAuthClicked),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = SportFinderLightColorScheme.primary,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewRegistrationScreen() {
    RegistrationScreen(
        state = RegistrationState(
            "some login",
            "some_password",
            null,
            false,
            false
        ),
        onLoginEnter = {},
        onPasswordEnter = {},
        onSingUpClicked = {},
        onNavigateToAuthClicked = {},
        onNavigateToProfileScreen = {},
    )
}