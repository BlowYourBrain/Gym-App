package com.simple.exercises

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.simple.exercises.navigation.AppNavHost
import com.simple.exercises.navigation.Screens
import com.simple.exercises.ui.DataProvider
import kotlinx.coroutines.launch


//BottomBar
private val FAB_SHAPE = RoundedCornerShape(50.dp)

//@Preview
//@Composable
//fun PreviewMainContent() {
//    App()
//}

@Composable
fun App(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    val topContentHeight = remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        bottomBar = { SetBottomAppBar() },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(
                            10
                        )
                    }
                },
                shape = FAB_SHAPE,
                backgroundColor = Color(0xFFFF8C00)
            ) {}
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            startScreen = Screens.OverviewScreen,
            modifier = Modifier.padding(innerPadding),
            listState = listState,
            topContentHeight = topContentHeight
        )
    }
}

//endregion

@Preview
@Composable
private fun BottomAppBarPreview() {
    SetBottomAppBar()
}

@Composable
private fun SetBottomAppBar() {
    BottomAppBar(
        cutoutShape = FAB_SHAPE
    ) {
        BottomNavigation() {

        }
    }
}