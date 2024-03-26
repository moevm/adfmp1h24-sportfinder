package ru.moevm.sportfinder.screen.sport_courts

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.screen.common_components.DefaultGoogleMap
import ru.moevm.sportfinder.screen.common_components.TopSearchBar
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme


@Composable
fun SportCourtMapScreen(
    startPoint: LatLng,
    state: SportCourtMapState,
    onTextForFilterChanged: (String) -> Unit,
    onFilterApply: () -> Unit,
    navigateToSportCourtListScreen: () -> Unit,
    navigateToSportCourtInfoScreen: (Int) -> Unit
) {
    val context = LocalContext.current
    val cameraPosition = rememberCameraPositionState(init = {
        position = CameraPosition.fromLatLngZoom(startPoint, 15.0f)
    })
    val (sportCourts, textForFilter) = state

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        DefaultGoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPosition
        ) {
            if (sportCourts.isNotEmpty()) {
                sportCourts.forEach { sportCourtItem ->
                    Marker(
                        state = MarkerState(sportCourtItem.coordinates),
                        title = sportCourtItem.name,
                        icon = BitmapDescriptorFactory
                            .fromBitmap(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.ic_sport_court_screen_map_marker
                                )!!.toBitmap()
                            ),
                        onInfoWindowClick = {
                            Handler(Looper.getMainLooper()).post {
                                navigateToSportCourtInfoScreen(sportCourtItem.courtId)
                            }
                        }
                    )
                }
            }
        }
        TopSearchBar(searchText = textForFilter, onTextSearchChanged = onTextForFilterChanged, onFilterApply = onFilterApply)

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = SportFinderLightColorScheme.primary,
                contentColor = SportFinderLightColorScheme.onPrimary
            ),
            shape = RoundedCornerShape(28.dp),
            onClick = {
                navigateToSportCourtListScreen()
            }
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sport_court_screen_list),
                    tint = SportFinderLightColorScheme.onPrimary,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.sport_court_screen_list_button_title).uppercase(),
                    color = SportFinderLightColorScheme.onPrimary
                )
            }
        }
    }
}
