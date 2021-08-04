package com.simple.exercises

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simple.exercises.ui.DataProvider
import kotlinx.coroutines.launch

//Common
private const val GREETINGS = "Hello World!"

//BottomBar
private val FAB_SHAPE = RoundedCornerShape(50.dp)

//Exercises
private val EXERCISE_CARD_HEIGHT = 86.dp
private val ROUNDED_CORNER_SIZE = 16.dp
private const val TEXT_MAX_LINES = 3

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@Preview
@Composable
fun PreviewMainContent() {
    MainContent()
}

@Composable
fun MainContent() {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    val topContentHeight = remember { mutableStateOf(0) }
    val data = DataProvider.extendedData(LocalContext.current)

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
    ) {
        Content(
            data = data,
            listState = listState,
            topContentHeight = topContentHeight
        )
    }
}

@Composable
fun Content(
    data: List<Exercise>,
    listState: LazyListState,
    topContentHeight: MutableState<Int>,
){
    Box(Modifier.fillMaxSize()){
        SetExercises(
            data = data,
            listState = listState,
            topContentHeight = topContentHeight
        )
        TopBar(
            listState = listState,
            topContentHeight = topContentHeight
        )
    }
}

@Composable
fun TopBar(
    listState: LazyListState,
    topContentHeight: MutableState<Int>,
) {
    Box(
        modifier = Modifier.graphicsLayer {
            if (listState.isFirstItem()){
                this.alpha = kotlin.math.min(
                    1f,
                    (listState.firstVisibleItemScrollOffset.toFloat() / topContentHeight.value)
                )
            }
        }
    ) {
        TopAppBar(
            elevation = 2.dp
        ) {
            Text(
                text = GREETINGS,
                style = typography.h4
            )
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SetExercises(
    data: List<Exercise>,
    listState: LazyListState,
    topContentHeight: MutableState<Int>,
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { topContentHeight.value = it.size.height }
    ) {
        item() {
            Box(
                modifier = Modifier
                    .fillParentMaxHeight(0.5f)
                    .fillParentMaxWidth()
                    .onGloballyPositioned { topContentHeight.value = it.size.height }
                    .graphicsLayer {
                        if (listState.isFirstItem()){
                            this.alpha = kotlin.math.min(
                                1f,
                                1 - (listState.firstVisibleItemScrollOffset.toFloat() / topContentHeight.value)
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    style = typography.h1,
                    textAlign = TextAlign.Center,
                    text = GREETINGS
                )
            }
        }
        items(data) {
            ExerciseCard(exercise = it)
        }
    }
}

private fun LazyListState.isFirstItem() = firstVisibleItemIndex == 0

@Composable
fun ExerciseCard(exercise: Exercise) =
    Card(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable { exercise.onClickAction.invoke() },
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(size = ROUNDED_CORNER_SIZE)
    )
    {
        Row()
        {
            Image(
                painterResource(id = R.drawable.weightlifter),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(EXERCISE_CARD_HEIGHT)
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(ROUNDED_CORNER_SIZE)))
            )
            Text(
                text = exercise.name,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.CenterVertically),
                style = typography.h3,
                maxLines = TEXT_MAX_LINES
            )
        }
    }

data class Exercise(
    val name: String,
    @DrawableRes
    val image: Int,
    val onClickAction: () -> Unit
)

//endregion

@Preview
@Composable
fun BottomAppBarPreview() {
    SetBottomAppBar()
}

@Composable
fun SetBottomAppBar() {
    BottomAppBar(
        cutoutShape = FAB_SHAPE
    ) {
        BottomNavigation() {

        }
    }
}