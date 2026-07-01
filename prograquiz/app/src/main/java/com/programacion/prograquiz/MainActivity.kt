package com.programacion.prograquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.programacion.prograquiz.navigation.PrograQuizNavHost
import com.programacion.prograquiz.ui.theme.BackgroundDark
import com.programacion.prograquiz.ui.theme.PrograQuizTheme
import com.programacion.prograquiz.viewmodel.SessionViewModel

class MainActivity : ComponentActivity() {

    private val sessionViewModel: SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrograQuizTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = BackgroundDark) {
                    PrograQuizNavHost(
                        navController    = rememberNavController(),
                        sessionViewModel = sessionViewModel
                    )
                }
            }
        }
    }
}
