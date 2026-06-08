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

// Pantalla principal del juego
@Composable
fun GameScreen(

    navController: NavController,
    viewModel: GameViewModel
) {

    // Obtiene el color actual
    val currentColor by viewModel.currentColor.collectAsState()

    // Obtiene el puntaje actual
    val score by viewModel.score.collectAsState()

    // Obtiene el tiempo restante
    val timeLeft by viewModel.timeLeft.collectAsState()

    // Obtiene el mensaje de correcto o incorrecto
    val message by viewModel.message.collectAsState()

    // Obtiene el contexto actual de la aplicación
    val context = LocalContext.current

    // Crea el administrador de sonidos
    val soundManager = SoundManager(context)

    // Inicia la música de fondo
    LaunchedEffect(Unit) {

        viewModel.startGame()
        soundManager.startBackgroundMusic()
    }

    // Si el tiempo terminó muestra el resultado
    LaunchedEffect(message) {
        if (message == "FIN") {
            navController.navigate("result")
        }
    }
    DisposableEffect(Unit) {

        onDispose {
            soundManager.stopBackgroundMusic()
        }
    }

    // Columna principal de la interfaz
    Column(
        modifier = Modifier

            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFEC4899),
                        Color(0xFF86198F)
                    )
                )
            )

            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Texto del temporizador
        Text(
            text = "Tiempo: $timeLeft",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Texto del puntaje
        Text(
            text = "Puntaje: $score",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Caja que muestra el color actual
        Box(
            modifier = Modifier
                .size(220.dp)
                .background(currentColor)
        )

        Spacer(modifier = Modifier.height(70.dp))

        // Primera fila de botones
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            // Botón rojo
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

                // Texto del botón
                Text(
                    "Rojo",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            // Botón azul
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

                // Texto del botón
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

            // Botón verde
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

                // Texto del botón
                Text(
                    "Verde",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            // Botón amarillo
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

                // Texto del botón
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