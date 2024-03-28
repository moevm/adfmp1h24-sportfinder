package ru.moevm.sportfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import ru.moevm.sportfinder.common.Utils
import ru.moevm.sportfinder.screen.common_components.BottomNavItem
import ru.moevm.sportfinder.screen.common_components.common_top_bar.CommonTopBar
import ru.moevm.sportfinder.screen.navigation.MainNavHost
import ru.moevm.sportfinder.screen.navigation.NavigationController
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme
import ru.moevm.sportfinder.ui.theme.SportFinderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shareText = { text: String ->
            Utils.shareText(this, text)
        }

        setContent {
            val viewModel = hiltViewModel<MainActivityViewModel>()
            val navHostController = rememberNavController()
            val navigationController = remember { NavigationController(navHostController) }

            val bottomBarItems = remember { getBottomItems(navigationController).toPersistentList() }
            val state by viewModel.state.collectAsStateWithLifecycle()
            val (isSupportedBottomNav, bottomBarSelectedItem, isSupportedTopBar, topBarType) = state

            LaunchedEffect(true) {
                viewModel.checkIsFirstStart()
            }

            SportFinderTheme {
                Scaffold(
                    bottomBar = {
                        if (isSupportedBottomNav) {
                            BottomBar(
                                selectedItem = bottomBarSelectedItem,
                                items = bottomBarItems,
                                onNavigateToScreen = viewModel::onBottomBarNewItemSelect
                            )
                        }
                    },
                    topBar = {
                        if (isSupportedTopBar) {
                            CommonTopBar(type = topBarType)
                        }
                    }
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        MainNavHost(
                            navigationController = navigationController,
                            updateBottomBarVisible = viewModel::onBottomBarVisibleStateUpdate,
                            updateTopBarType = viewModel::onTopBarTypeUpdated,
                            shareText = shareText
                        )
                    }
                }
            }
        }
    }

    private fun getBottomItems(navigationController: NavigationController) = listOf(
        BottomNavItem(
            R.drawable.ic_bottom_bar_profile,
            navigationController::navigateToProfile
        ),
        BottomNavItem(
            R.drawable.ic_bottom_bar_sport_court,
            navigationController::navigateToSportCourt
        ),
        BottomNavItem(
            R.drawable.ic_bottom_bar_running_track,
            navigationController::navigateToRunning
        ),
        BottomNavItem(
            R.drawable.ic_bottom_bar_training,
            navigationController::navigateToTraining
        ),
    )
}

@Composable
private fun BottomBar(
    selectedItem: Int,
    items: ImmutableList<BottomNavItem>,
    onNavigateToScreen: (Int) -> Unit,
) {
    BottomNavigation(
        backgroundColor = SportFinderLightColorScheme.primary
    ) {
        items.forEachIndexed { index, bottomNavItem ->
            BottomNavigationItem(
                selected = selectedItem == index,
                onClick = {
                    if (selectedItem != index) {
                        onNavigateToScreen(index)
                        bottomNavItem.onItemClick()
                    }
                },
                selectedContentColor = SportFinderLightColorScheme.onPrimary,
                unselectedContentColor = SportFinderLightColorScheme.onPrimary,
                icon = {
                    Icon(
                        painter = painterResource(bottomNavItem.iconId),
                        contentDescription = null
                    )
                }
            )
        }
    }
}