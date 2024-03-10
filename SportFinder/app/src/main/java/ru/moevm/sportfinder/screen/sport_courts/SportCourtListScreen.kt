package ru.moevm.sportfinder.screen.sport_courts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.screen.common_components.TopSearchBar
import ru.moevm.sportfinder.screen.common_components.shimmerEffect
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme


@Composable
fun SportCourtsListScreen(
    state: SportCourtListState,
    onTextForFilterChanged: (String) -> Unit,
    onFilterApply: () -> Unit,
    navigateToSportCourtMapScreen: () -> Unit,
) {
    val (listOfSportCourt, textForFilter, isLoading) = state

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TopSearchBar(textForFilter, onTextForFilterChanged, onFilterApply)

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if (isLoading) {
                    items(3) {
                        Box(
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                                .padding(10.dp)
                                .shimmerEffect()
                        )
                    }
                } else {
                    items(listOfSportCourt) {
                        SportCourtListItem(it)
                    }
                }
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = SportFinderLightColorScheme.primary,
                contentColor = SportFinderLightColorScheme.onPrimary
            ),
            shape = RoundedCornerShape(28.dp),
            onClick = navigateToSportCourtMapScreen
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sport_court_screen_map),
                    tint = SportFinderLightColorScheme.onPrimary,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.sport_court_screen_map_button_title).uppercase(),
                    color = SportFinderLightColorScheme.onPrimary
                )
            }
        }

    }
}

@Composable
private fun SportCourtListItem(
    sportCourtListItem: SportCourtListItemVO
) {
    val (courtId, name, tags, distance, temperature, resourceId) = sportCourtListItem

    Box(
        modifier = Modifier
            .border(BorderStroke(2.dp, SportFinderLightColorScheme.primary), RoundedCornerShape(5))
            .fillMaxWidth()
    ) {
        Column {
            Row {
                val longImageSide = 100
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = "Court image",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(longImageSide.dp, longImageSide.dp * 2 / 3)
                )
                Column(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = tags.joinToString(separator = ", "),
                        style = MaterialTheme.typography.titleSmall,
                        color = SportFinderLightColorScheme.onSecondary
                    )

                }

            }
            Row(
                modifier = Modifier.padding(5.dp)
            ) {
                val courtAttributesModifier = Modifier.padding(end = 10.dp)
                val courtIconsAttributesModifier = Modifier.padding(end = 8.dp)
                distance?.let {
                    Icon(
                        painter = painterResource(R.drawable.ic_sport_court_screen_distance),
                        contentDescription = "Map sign",
                        tint = SportFinderLightColorScheme.primary,
                        modifier = courtIconsAttributesModifier
                    )
                    Text(
                        text = distance.toString() + "Km",
                        modifier = courtAttributesModifier,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                temperature?.let {
                    Icon(
                        painter = painterResource(R.drawable.ic_sport_court_screen_weather),
                        contentDescription = "Temperature sign",
                        tint = SportFinderLightColorScheme.primary,
                        modifier = courtIconsAttributesModifier
                    )

                    Text(
                        text = if (temperature > 0) "+" + temperature.toString() + "C"
                        else temperature.toString() + "C",
                        modifier = courtAttributesModifier,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

            }
        }
    }
}


@Preview
@Composable
fun SportsCourtListScreenPreview() {
    SportCourtsListScreen(
        state = SportCourtListState(
            listOfSportCourt = persistentListOf(
                SportCourtListItemVO(
                    courtId = 0,
                    name = "Старая деревня",
                    tags = persistentListOf("Вкусно"),
                    distance = 0.3F,
                    temperature = 13.4F,
                    imagePlaceholder = R.drawable.no_image_placeholder
                ),
            ),
            textForFilter = "",
            isLoading = false
        ),
        onTextForFilterChanged = {}, onFilterApply = {}, navigateToSportCourtMapScreen = {}
    )
}

@Composable
@Preview
private fun SportCourtListItemPreview() {
    SportCourtListItem(
        SportCourtListItemVO(
            courtId = 0,
            name = "Старая деревня",
            tags = persistentListOf("Вкусно"),
            distance = 0.3F,
            temperature = 13.4F,
            imagePlaceholder = R.drawable.no_image_placeholder
        )
    )
}