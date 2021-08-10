package com.simple.exercises.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.simple.exercises.DetailScreenBody
import com.simple.exercises.OverviewBody
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
            OverviewBody(DataProvider.extendedData(LocalContext.current), listState, topContentHeight)
        }

        composable(Screens.DetailScreen.name){
            DetailScreenBody()
        }

        composable(Screens.SplashScreen.name){

        }
    }
}
