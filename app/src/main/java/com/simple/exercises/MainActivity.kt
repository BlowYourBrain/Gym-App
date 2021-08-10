package com.simple.exercises

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simple.exercises.navigation.AppNavHost
import com.simple.exercises.navigation.Screens


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            App(navController)
        }
    }
}

@Composable
fun App(navController: NavHostController){
    AppNavHost(
        navController = navController,
        startScreen = Screens.OverviewScreen
    )
}