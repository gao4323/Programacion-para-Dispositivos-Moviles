package com.example.parcial


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

// Actividad principal de la aplicación
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}