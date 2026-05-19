package com.example.parcial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel
) {

    val currentColor by viewModel.currentColor.collectAsState()
    val score by viewModel.score.collectAsState()
    val timeLeft by viewModel.timeLeft.collectAsState()
    val message by viewModel.message.collectAsState()

    val context = LocalContext.current
    val soundManager = SoundManager(context)

    // Inicia el juego
    LaunchedEffect(Unit) {
        viewModel.startGame()
        soundManager.startBackgroundMusic()
    }

    // Cuando el tiempo llegue a 0
    LaunchedEffect(timeLeft) {

        if (timeLeft == 0) {
            soundManager.stopBackgroundMusic()
            navController.navigate("result")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFEC4899), Color(0xFF86198F))
                )
            )
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Tiempo: $timeLeft",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Puntaje
        Text(
            text = "Puntaje: $score",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Cuadro de color
        Box(
            modifier = Modifier
                .size(220.dp)
                .background(currentColor)
        )

        Spacer(modifier = Modifier.height(70.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            Button(
                onClick = {

                    if (currentColor == Color.Red) {
                        soundManager.playCorrect()
                    } else {
                        soundManager.playWrong()
                    }

                    viewModel.selectColor(Color.Red)

                },
                modifier = Modifier
                    .width(140.dp)
                    .height(60.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF4444)
                )
            ) {
                Text(
                    "Rojo",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Button(
                onClick = {

                    if (currentColor == Color.Blue) {
                        soundManager.playCorrect()
                    } else {
                        soundManager.playWrong()
                    }

                    viewModel.selectColor(Color.Blue)

                },
                modifier = Modifier
                    .width(140.dp)
                    .height(60.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3B82F6)
                )
            ) {
                Text(
                    "Azul",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Segunda fila de botones
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            Button(
                onClick = {

                    if (currentColor == Color.Green) {
                        soundManager.playCorrect()
                    } else {
                        soundManager.playWrong()
                    }

                    viewModel.selectColor(Color.Green)

                },
                modifier = Modifier
                    .width(140.dp)
                    .height(60.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF10B981)
                )
            ) {
                Text(
                    "Verde",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Button(
                onClick = {

                    if (currentColor == Color.Yellow) {
                        soundManager.playCorrect()
                    } else {
                        soundManager.playWrong()
                    }

                    viewModel.selectColor(Color.Yellow)

                },
                modifier = Modifier
                    .width(140.dp)
                    .height(60.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF59E0B),
                )
            ) {
                Text(
                    "Amarillo",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Mensaje de correcto o incorrecto
        Text(
            text = message,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}