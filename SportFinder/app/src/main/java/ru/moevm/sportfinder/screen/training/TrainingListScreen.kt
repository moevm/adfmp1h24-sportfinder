package ru.moevm.sportfinder.screen.training

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.persistentListOf
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.screen.common_components.TopSearchBar
import ru.moevm.sportfinder.screen.common_components.shimmerEffect
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme

@Composable
fun TrainingListScreen(
    state: TrainingListState,
    onTextForFilterChanged: (String) -> Unit,
    onFilterApply: () -> Unit,
    navigateToCreateTrainingScreen: () -> Unit,
    navigateToTrainingInfoScreen: (Long) -> Unit,
) {
    val (listOfTraining, textForFilter, isLoading) = state

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
                item {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = navigateToCreateTrainingScreen,
                        colors = ButtonDefaults.buttonColors(backgroundColor = SportFinderLightColorScheme.primary)
                    ) {
                        Text(
                            text = stringResource(id = R.string.training_screen_list_add_training_title),
                            fontSize = 14.sp,
                            color = SportFinderLightColorScheme.onPrimary
                        )
                    }
                }
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
                    items(listOfTraining) { trainingListItem ->
                        TrainingListItem(
                            trainingListItem = trainingListItem,
                            navigateToTrainingInfoScreen = navigateToTrainingInfoScreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TrainingListItem(
    trainingListItem: TrainingListItemVO,
    navigateToTrainingInfoScreen: (Long) -> Unit,
) {
    val (courtId, name, tags, distance, temperature) = trainingListItem

    Box(
        modifier = Modifier
            .border(BorderStroke(2.dp, SportFinderLightColorScheme.primary), RoundedCornerShape(5))
            .fillMaxWidth()
            .clickable { navigateToTrainingInfoScreen(courtId) }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .padding(top = 4.dp)
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
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                val courtAttributesModifier = Modifier.padding(end = 8.dp)
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
private fun TrainingListScreenPreview() {
    TrainingListScreen(
        state = TrainingListState(
            listOfTraining = persistentListOf(
                TrainingListItemVO(
                    trainingId = 0,
                    name = "Старая деревня",
                    tags = persistentListOf("Вкусно"),
                    distance = 0.3F,
                    temperature = 13.4F,
                ),
            ),
            textForFilter = "",
            isLoading = false
        ),
        onTextForFilterChanged = {},
        onFilterApply = {},
        navigateToCreateTrainingScreen = {},
        navigateToTrainingInfoScreen = {}
    )
}

@Composable
@Preview
private fun TrainingListItemPreview() {
    TrainingListItem(
        TrainingListItemVO(
            trainingId = 0,
            name = "Старая деревня",
            tags = persistentListOf("Вкусно"),
            distance = 0.3F,
            temperature = 13.4F,
        ),
        {},
    )
}