package com.simple.exercises

import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailScreenBody(){
    Text(text = "It's a Detail Screen!", style = typography.h1)
}