package com.simple.exercises.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.simple.exercises.*

sealed class Screens(val name: String) {

    object OverviewScreen : Screens("OverviewScreen")
    object SplashScreen: Screens("SplashScreen")
    object DetailScreen: Screens("DetailScreen")
}