package com.simple.exercises

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simple.exercises.ui.DataProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetExercises(data = DataProvider.extendedData(this))
        }
    }
}

@Preview
@Composable
fun SetExercisesPreview() {
    val data = DataProvider.getData(null)
    SetExercises(data)
}

@Composable
fun SetExercises(data: List<Exercise>) {
    LazyColumn() {
        items(data) {
            ExerciseCard(exercise = it)
        }
    }
}

private val EXERCISE_CARD_HEIGHT = 86.dp
private val ROUNDED_CORNER_SIZE = 16.dp
private const val TEXT_MAX_LINES = 3

@Composable
fun ExerciseCard(exercise: Exercise) =
    Card(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable { exercise.onClickAction.invoke() }
        ,
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
                modifier = Modifier.padding(end = 8.dp),
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