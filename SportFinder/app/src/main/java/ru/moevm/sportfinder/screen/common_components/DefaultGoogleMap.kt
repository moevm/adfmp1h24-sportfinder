package ru.moevm.sportfinder.screen.common_components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

@Composable
fun DefaultGoogleMap(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    onMapClick: (LatLng) -> Unit = {},
    onMapLongClick: (LatLng) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isBuildingEnabled = true),
        uiSettings = MapUiSettings(
            mapToolbarEnabled = false,
            zoomControlsEnabled = false
        ),
        onMapClick = onMapClick,
        onMapLongClick = onMapLongClick
    ) {
        content()
    }
}