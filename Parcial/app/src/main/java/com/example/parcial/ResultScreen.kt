package com.example.parcial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ResultScreen(
    navController: NavController,
    viewModel: GameViewModel
) {
    val score by viewModel.score.collectAsState()
    val history by viewModel.history.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFEC4899), Color(0xFF86198F))
                )
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        // Título
        Text(
            text = "Juego Terminado",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Puntaje final
        Text(
            text = "Puntaje final: $score",
            fontSize = 26.sp,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Puntaje más alto
        Text(
            text = "Puntaje más alto: ${viewModel.getHighScore()}",
            fontSize = 24.sp,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Historial
        Text(
            text = "Historial",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Lista de partidas
        LazyColumn(
            modifier = Modifier.height(250.dp)
        ) {
            items(history) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFEC4899)
                    )
                ) {
                    Text(
                        text = "Partida ${item.gameNumber}: ${item.score} puntos",
                        modifier = Modifier.padding(15.dp),
                        fontSize = 20.sp,
                        color = Color(0xFFFFFFFF)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Botón Jugar Otra Vez
        Button(
            onClick = {
                navController.navigate("game")
            },
            modifier = Modifier
                .width(220.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFFFFF),
                contentColor = Color(0xFF86198F)
            )
        ) {
            Text(
                text = "Jugar otra vez",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}