package com.simple.exercises.ui

import android.content.Context
import android.widget.Toast
import com.simple.exercises.Exercise
import com.simple.exercises.R

object DataProvider {

    fun getData(context: Context?) = listOf(
        Exercise("Monday", R.drawable.weightlifter) { toast(context) },
        Exercise("Monday, Tuesday, Wendesday", R.drawable.weightlifter) { toast(context) },
        Exercise("Давайте поговорим о Toyota Supra в кузове А80!", R.drawable.weightlifter) {
            toast(
                context
            )
        }
    )

    fun extendedData(context: Context?): List<Exercise> {
        val list = mutableListOf<Exercise>()
        repeat(50) {
            list.add(
                Exercise(
                    "index $it",
                    R.drawable.weightlifter
                ) { toast(context) }
            )
        }

        return list
    }

    private fun toast(context: Context?) {
        Toast.makeText(context, "Hello, motherfucker!", Toast.LENGTH_SHORT).show()
    }

}