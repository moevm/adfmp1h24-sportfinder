package ru.moevm.sportfinder.screen.running

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.JointType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.common.Constants
import ru.moevm.sportfinder.screen.common_components.DefaultGoogleMap
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RunningInfoScreen(
    state: RunningInfoState,
) {
    val context = LocalContext.current
    val (title, listOfPoints, distance, listOfTags) = state

    val cameraPosition = rememberCameraPositionState(init = {
        position = CameraPosition.fromLatLngZoom(listOfPoints.firstOrNull() ?: Constants.SPB_CENTER_POINT, 15.0f)
    })

    Box {
        DefaultGoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPosition
        ) {
            if (listOfPoints.isNotEmpty()) {
                listOfPoints.forEach {
                    Marker(
                        state = MarkerState(it),
                        anchor = Offset(0.5f, 0.5f),
                        icon = BitmapDescriptorFactory
                            .fromBitmap(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.ic_maps_placemark_marker
                                )!!.toBitmap()
                            ),
                        flat = true
                    )
                }
                if (listOfPoints.size > 1) {
                    Polyline(
                        points = listOfPoints,
                        jointType = JointType.ROUND,
                        color = SportFinderLightColorScheme.primary,
                        width = 12f
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SportFinderLightColorScheme.onPrimary.copy(alpha = 0.8f))
                .padding(8.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                itemsIndexed(listOfTags) { _, tag ->
                    Chip(
                        colors = ChipDefaults.chipColors(
                            backgroundColor = SportFinderLightColorScheme.primary,
                            leadingIconContentColor = SportFinderLightColorScheme.onPrimary
                        ),
                        onClick = {}
                    ) {
                        Text(tag, color = SportFinderLightColorScheme.onPrimary)
                    }
                }
            }
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_sport_court_screen_distance),
                    contentDescription = "Map sign",
                    tint = SportFinderLightColorScheme.primary,
                )
                Text(
                    text = "Дистанция: ${"%.2f".format(distance)}м",
                    modifier = Modifier.padding(start = 4.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
