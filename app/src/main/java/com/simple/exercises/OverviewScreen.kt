package com.simple.exercises

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.min

//Exercises
private val EXERCISE_CARD_HEIGHT = 86.dp
private val ROUNDED_CORNER_SIZE = 16.dp
private const val TEXT_MAX_LINES = 3

//Common
private const val GREETINGS = "Hello World!"

@Composable
fun OverviewBody(
    data: List<Exercise>,
    listState: LazyListState,
    topContentHeight: MutableState<Int>,
    onClickAction: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
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
private fun TopBar(
    listState: LazyListState,
    topContentHeight: MutableState<Int>,
) {
    Box(
        modifier = Modifier.graphicsLayer {
            if (listState.isFirstItem()) {
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
                style = MaterialTheme.typography.h4
            )
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SetExercises(
    data: List<Exercise>,
    listState: LazyListState,
    topContentHeight: MutableState<Int>,
    onClickAction: () -> Unit
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
                        if (listState.isFirstItem()) {
                            this.alpha = min(
                                1f,
                                1 - (listState.firstVisibleItemScrollOffset.toFloat() / topContentHeight.value)
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    style = MaterialTheme.typography.h1,
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
private fun ExerciseCard(exercise: Exercise) =
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
                style = MaterialTheme.typography.h3,
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