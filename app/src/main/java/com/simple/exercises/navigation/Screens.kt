package com.simple.exercises.navigation

sealed class Screens(val name: String) {

    object OverviewScreen : Screens("OverviewScreen")
    object SplashScreen: Screens("SplashScreen")
    object DetailScreen: Screens("DetailScreen")
}