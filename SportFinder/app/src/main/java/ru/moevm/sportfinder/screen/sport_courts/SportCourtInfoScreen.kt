package ru.moevm.sportfinder.screen.sport_courts

import android.view.MotionEvent
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.common.Constants
import ru.moevm.sportfinder.screen.common_components.DefaultGoogleMap
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme
import java.text.DecimalFormat

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SportCourtInfoScreen(
    state: SportCourtInfoState,
    onFavoritesClicked: () -> Unit,
) {
    val (courtName, courtAddress, courtInitialPoint, courtDistance, courtWeatherTemperature, courtAmountFavorites, isFavorite, courtTags, courtInfo, courtLinkUrl) = state
    val context = LocalContext.current
    val cameraPosition = rememberCameraPositionState(init = {
        position = CameraPosition.fromLatLngZoom(courtInitialPoint ?: Constants.SPB_CENTER_POINT, 15.0f)
    })
    var columnScrollingEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(courtInitialPoint) {
        cameraPosition.move(CameraUpdateFactory.newLatLng(courtInitialPoint ?: Constants.SPB_CENTER_POINT))
    }

    LaunchedEffect(cameraPosition.isMoving) {
        if (!cameraPosition.isMoving) {
            columnScrollingEnabled = true
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .verticalScroll(rememberScrollState(), enabled = columnScrollingEnabled),
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
            shape = RoundedCornerShape(8.dp)
        ) {
            DefaultGoogleMap(
                modifier = Modifier
                    .height((LocalConfiguration.current.screenHeightDp / 2).dp)
                    .fillMaxWidth()
                    .pointerInteropFilter { event ->
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                columnScrollingEnabled = false
                                false
                            }

                            else -> {
                                true
                            }
                        }
                    }
                ,
                cameraPositionState = cameraPosition
            ) {
                if (courtInitialPoint != null) {
                    Marker(
                        state = MarkerState(courtInitialPoint),
                        title = courtName,
                        icon = BitmapDescriptorFactory
                            .fromBitmap(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.ic_sport_court_screen_map_marker
                                )!!.toBitmap()
                            )
                    )
                }

            }
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = SportFinderLightColorScheme.primary,
        ) {
            SelectionContainer {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = courtName.uppercase(),
                        style = TextStyle(
                            color = SportFinderLightColorScheme.onPrimary,
                            fontSize = 16.sp
                        )
                    )
                    Text(
                        text = courtAddress.uppercase(),
                        style = TextStyle(
                            color = SportFinderLightColorScheme.onPrimary,
                            fontSize = 12.sp
                        )
                    )
                }
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
                Chip(
                    colors = ChipDefaults.chipColors(
                        backgroundColor = SportFinderLightColorScheme.primary,
                        leadingIconContentColor = SportFinderLightColorScheme.onPrimary
                    ),
                    onClick = {}
                ) {
                    Text(item, color = SportFinderLightColorScheme.onPrimary)
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
        SelectionContainer {
            Text(
                text = courtInfo,
                style = TextStyle(fontSize = 14.sp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
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