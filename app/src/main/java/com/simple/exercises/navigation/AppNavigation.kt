package com.simple.exercises.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.simple.exercises.ui.DataProvider

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startScreen: Screens,
    listState: LazyListState,
    topContentHeight: MutableState<Int>
) {
    NavHost(
        navController = navController,
        startDestination = startScreen.name,
        modifier = modifier
    ) {
        composable(Screens.OverviewScreen.name) {
            Screens.OverviewScreen.getScreen(
                data = DataProvider.extendedData(LocalContext.current),
                listState = listState,
                topContentHeight = topContentHeight,
                onListItemClick = {

                }
            )
        }

        composable(Screens.DetailScreen.name){

        }

        composable(Screens.SplashScreen.name){

        }
    }
}
