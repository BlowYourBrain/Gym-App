package com.simple.exercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun SplashScreenPreview(){
    SplashScreenBody()
}

@Composable
fun SplashScreenBody(){
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize(0.5f),
        contentAlignment = Alignment.Center
    ){
        Image(painter = painterResource(id = R.drawable.weightlifter), contentDescription = "")
    }
}