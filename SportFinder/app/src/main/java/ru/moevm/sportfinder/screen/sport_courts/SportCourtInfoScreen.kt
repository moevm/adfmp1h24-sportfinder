package ru.moevm.sportfinder.screen.sport_courts

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.rememberCameraPositionState
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.screen.common_components.DefaultGoogleMap
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme
import java.text.DecimalFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SportCourtInfoScreen(
    state: SportCourtInfoState,
    isShareUrlClicked: Boolean,
    onSharedUrlShown: () -> Unit,
    onFavoritesClicked: () -> Unit,
) {
    val (courtName, courtAddress, courtInitialPoint, courtDistance, courtWeatherTemperature, courtAmountFavorites, isFavorite, courtTags, courtInfo, courtLinkUrl) = state
    val context = LocalContext.current

    LaunchedEffect(isShareUrlClicked) {
        if (isShareUrlClicked) {
            Toast.makeText(context, courtLinkUrl, Toast.LENGTH_LONG).show()
            onSharedUrlShown()
        }
    }

    val cameraPosition = rememberCameraPositionState().apply {
        position = CameraPosition.fromLatLngZoom(courtInitialPoint, 15.0f)
    }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(12.dp)
            .scrollable(scrollState, Orientation.Vertical),
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp)
        ) {
            DefaultGoogleMap(
                modifier = Modifier
                    .height((LocalConfiguration.current.screenHeightDp / 2).dp)
                    .fillMaxWidth(),
                cameraPositionState = cameraPosition
            ) {

            }
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = SportFinderLightColorScheme.primary,
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)) {
                Text(
                    text = courtName.uppercase(),
                    style = TextStyle(color = SportFinderLightColorScheme.onPrimary, fontSize = 16.sp)
                )
                Text(
                    text = courtAddress.uppercase(),
                    style = TextStyle(color = SportFinderLightColorScheme.onPrimary, fontSize = 12.sp)
                )
            }
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = SportFinderLightColorScheme.secondary,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconWithText(
                    iconId = R.drawable.ic_sport_court_screen_distance,
                    text = DecimalFormat("###.##км").format(courtDistance)
                )
                IconWithText(
                    iconId = R.drawable.ic_sport_court_screen_weather,
                    text = "${withSigh(courtWeatherTemperature)}C"
                )
                IconWithText(
                    iconId = R.drawable.ic_sport_court_screen_favorite_active,
                    text = "$courtAmountFavorites"
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onFavoritesClicked,
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = if (isFavorite) {
                            painterResource(id = R.drawable.ic_sport_court_screen_favorite_active)
                        } else {
                            painterResource(id = R.drawable.ic_sport_court_screen_favorite_inactive)
                        },
                        tint = SportFinderLightColorScheme.onPrimary,
                        contentDescription = null
                    )
                }
            }
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            items(courtTags) { item ->
                Chip(onClick = {}) {
                    Text(item, color = SportFinderLightColorScheme.onSurface)
                }
            }
        }
        Row {
            Icon(
                painter = painterResource(R.drawable.ic_sport_court_screen_info),
                tint = SportFinderLightColorScheme.primary,
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.sport_court_screen_info_information_title),
                style = TextStyle(fontSize = 20.sp)
            )
        }
        Text(
            text = courtInfo,
            style = TextStyle(fontSize = 14.sp)
        )
    }
}

private fun withSigh(value: Int): String {
    return if (value < 0) {
        "-$value"
    } else if (value > 0) {
        "+$value"
    } else {
        value.toString()
    }
}

@Composable
private fun IconWithText(
    @DrawableRes iconId: Int,
    text: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = iconId), tint = SportFinderLightColorScheme.onPrimary, contentDescription = null)
        Text(text = text.uppercase(), style = TextStyle(color = SportFinderLightColorScheme.onPrimary, fontSize = 20.sp))
    }
}