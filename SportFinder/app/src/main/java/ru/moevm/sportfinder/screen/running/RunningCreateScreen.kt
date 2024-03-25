package ru.moevm.sportfinder.screen.running

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
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
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.screen.common_components.CommonButton
import ru.moevm.sportfinder.screen.common_components.CommonTextField
import ru.moevm.sportfinder.screen.common_components.DefaultGoogleMap
import ru.moevm.sportfinder.screen.common_components.SelectListAlertDialog
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RunningCreateScreen(
    startPoint: LatLng,
    state: RunningCreateState,
    onTitleChanged: (String) -> Unit,
    addPoint: (LatLng) -> Unit,
    removePoint: (LatLng) -> Unit,
    onShowSelectTagsDialogClick: () -> Unit,
    onSaveTagsDialogClick: (List<Int>) -> Unit,
    onDismissTagsDialogClick: () -> Unit,
    onRemoveTagClick: (Int) -> Unit,
    onSaveClick: () -> Unit
) {
    val context = LocalContext.current
    val cameraPosition = rememberCameraPositionState(init = {
        position = CameraPosition.fromLatLngZoom(startPoint, 15.0f)
    })

    val (title, listOfPoints, distance, listOfTags, availableTags, isSelectTagDialogShown) = state

    if (isSelectTagDialogShown) {
        SelectListAlertDialog(
            listItems = availableTags,
            onSaveClick = onSaveTagsDialogClick,
            onDismiss = onDismissTagsDialogClick
        )
    }

    Box {
        DefaultGoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPosition,
            onMapClick = addPoint,
            onMapLongClick = removePoint
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
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(SportFinderLightColorScheme.primary, shape = CircleShape)
                        .paint(
                            painterResource(id = R.drawable.ic_running_create_screen_help),
                            contentScale = ContentScale.Inside
                        )
                        .clickable {
                            Toast.makeText(context, "Чтобы добавить точку маршрута - нажмите один раз на карту. Чтобы удалить точку маршрута - удерживайте над существующей точкой", Toast.LENGTH_LONG).show()
                        }
                )
            }

            CommonTextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = title,
                isSingleLine = true,
                hint = "Название маршрута",
                onTextChanged = onTitleChanged
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                itemsIndexed(listOfTags) { index, tag ->
                    Chip(
                        colors = ChipDefaults.chipColors(
                            backgroundColor = SportFinderLightColorScheme.primary,
                            leadingIconContentColor = SportFinderLightColorScheme.onPrimary
                        ),
                        onClick = {},
                        leadingIcon = { Icon(Icons.Outlined.Clear, null, modifier = Modifier.clickable { onRemoveTagClick(index) }) }
                    ) {
                        Text(tag, color = SportFinderLightColorScheme.onPrimary)
                    }
                }
            }
            CommonButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onShowSelectTagsDialogClick
            ) {
                Text(text = "Добавить теги", color = SportFinderLightColorScheme.onPrimary)
            }
            CommonButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onSaveClick
            ) {
                Text(text = "Сохранить", color = SportFinderLightColorScheme.onPrimary)

            }
        }
    }
}
