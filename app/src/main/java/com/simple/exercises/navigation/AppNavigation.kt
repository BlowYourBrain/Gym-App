package com.simple.exercises.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.simple.exercises.DetailScreenBody
import com.simple.exercises.OverviewBody

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startScreen: Screens
) {
    NavHost(
        navController = navController,
        startDestination = startScreen.name,
        modifier = modifier
    ) {
        composable(Screens.OverviewScreen.name) {
            OverviewBody(navController)
        }

        composable(Screens.DetailScreen.name){
            DetailScreenBody()
        }

        composable(Screens.SplashScreen.name){

        }
    }
}
