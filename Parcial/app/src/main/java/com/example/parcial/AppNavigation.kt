package com.example.parcial

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*


@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val gameViewModel: GameViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {

        composable("welcome") {
            WelcomeScreen(navController)
        }

        composable("game") {
            GameScreen(navController, gameViewModel)
        }

        composable("result") {
            ResultScreen(navController, gameViewModel)
        }
    }
}