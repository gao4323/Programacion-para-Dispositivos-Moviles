package com.example.prograquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.prograquiz.navigation.AppNavGraph
import com.example.prograquiz.ui.theme.PrograQuizTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            PrograQuizTheme {

                AppNavGraph()

            }
        }
    }
}