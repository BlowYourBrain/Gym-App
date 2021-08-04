package com.simple.exercises

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simple.exercises.ui.DataProvider
import kotlinx.coroutines.launch

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
    val scaffoldState = rememberScaffoldState()
    val offset = remember { mutableStateOf(0f) }
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
                        scaffoldState
                            .snackbarHostState
                            .showSnackbar("hello world!")
                    }
                },
                shape = FAB_SHAPE,
                backgroundColor = Color(0xFFFF8C00)
            ) {}
        }
    ) {
        SetExercises(
            data = data,
            offset = offset
        )
    }
}

@Composable
fun SetExercises(data: List<Exercise>, offset: MutableState<Float>) {
    val scrollState = rememberScrollState()
    val topContentHeight = remember { mutableStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                topContentHeight.value = it.size.height
            }
            .scrollable(scrollState, Orientation.Vertical)
            .nestedScroll(connection = object : NestedScrollConnection {
                override fun onPostScroll(
                    consumed: Offset,
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    offset.value = minOf(scrollState.maxValue.toFloat(),  maxOf(0f, (offset.value - consumed.y)))
                    return super.onPostScroll(consumed, available, source)
                }
            })
    ) {
        item() {
            Box(
                modifier = Modifier
                    .fillParentMaxHeight(0.5f)
                    .fillParentMaxWidth()
                    .onGloballyPositioned {
                        topContentHeight.value = it.size.height
                    }
                    .graphicsLayer {
                        alpha = kotlin.math.min(1f, 1 - (offset.value / topContentHeight.value))
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    style = typography.h1,
                    textAlign = TextAlign.Center,
                    text = "Hello world!"
                )
            }
        }
        items(data) {
            ExerciseCard(exercise = it)
        }
    }
}

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