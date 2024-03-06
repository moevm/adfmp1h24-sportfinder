package ru.moevm.sportfinder.screen.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.screen.profile.components.TabNavItem
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme

@Composable
fun ProfileScreen(
    state: ProfileState,
    onTabSwitch: (Int) -> Unit,
) {
    val (tabIndex, profileName) = state

    val tabs = listOf(
        TabNavItem(
            icon = painterResource(id = R.drawable.ic_profile_screen_favorite_inactive),
            text = stringResource(id = R.string.profile_screen_favorite_tab_title),
        ),
        TabNavItem(
            painterResource(id = R.drawable.ic_profile_screen_created_inactive),
            text = stringResource(id = R.string.profile_screen_created_tab_title),
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sportfinder_logo),
                    modifier = Modifier
                        .size(100.dp, 100.dp)
                        .clip(CircleShape),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = profileName.uppercase(),
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                    ),
                    fontSize = 14.sp,
                    color = SportFinderLightColorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            TabRow(selectedTabIndex = tabIndex, indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = SportFinderLightColorScheme.primary
                )
            }) {
                tabs.forEachIndexed { index, tabNavItem ->
                    val isSelected = tabIndex == index
                    Tab(
                        selected = isSelected,
                        onClick = {
                            onTabSwitch(index)
                        },
                        selectedContentColor = SportFinderLightColorScheme.primary,
                        unselectedContentColor = SportFinderLightColorScheme.secondary,
                        text = {
                            Text(text = tabNavItem.text)
                        },
                        icon = {
                            Icon(
                                painter = tabNavItem.icon,
                                tint = if (isSelected) {
                                    SportFinderLightColorScheme.primary
                                } else {
                                    SportFinderLightColorScheme.secondary
                                },
                                contentDescription = null
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            when (tabIndex) {
                0 -> FavoriteBlock({}, {}, {})
                1 -> CreatedBlock({}, {})
            }
        }
    }
}

@Composable
fun FavoriteBlock(
    onNavigateToTrainings: () -> Unit,
    onNavigateToRunningTrack: () -> Unit,
    onNavigateToSportCourt: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        NavigateButton(R.string.profile_screen_training_button_title, onNavigateToTrainings)
        NavigateButton(R.string.profile_screen_running_track_button_title, onNavigateToRunningTrack)
        NavigateButton(R.string.profile_screen_sport_courts_button_title, onNavigateToSportCourt)
    }
}

@Composable
fun CreatedBlock(
    onNavigateToTrainings: () -> Unit,
    onNavigateToRunningTrack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        NavigateButton(R.string.profile_screen_training_button_title, onNavigateToTrainings)
        NavigateButton(R.string.profile_screen_running_track_button_title, onNavigateToRunningTrack)
    }
}

@Composable
private fun NavigateButton(
    @StringRes textId: Int,
    navigateTo: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = SportFinderLightColorScheme.primary),
        shape = RoundedCornerShape(8.dp),
        onClick = navigateTo
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                color = SportFinderLightColorScheme.onPrimary,
                text = stringResource(id = textId).uppercase(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_profile_screen_arrow_next),
                tint = SportFinderLightColorScheme.onPrimary,
                contentDescription = null
            )
        }
    }
}
