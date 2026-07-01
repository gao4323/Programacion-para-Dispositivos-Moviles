package com.example.prograquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.prograquiz.navigation.PrograQuizNavHost
import com.example.prograquiz.ui.theme.BackgroundDark
import com.example.prograquiz.ui.theme.PrograQuizTheme
import com.example.prograquiz.viewmodel.SessionViewModel

class MainActivity : ComponentActivity() {

    private val sessionViewModel: SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrograQuizTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = BackgroundDark
                ) {
                    val navController = rememberNavController()
                    PrograQuizNavHost(
                        navController    = navController,
                        sessionViewModel = sessionViewModel
                    )
                }
            }
        }
    }
}
