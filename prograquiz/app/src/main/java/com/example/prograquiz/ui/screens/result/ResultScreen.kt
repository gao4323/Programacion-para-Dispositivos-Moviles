package com.example.prograquiz.ui.screens.result


import androidx.compose.foundation.layout.*

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.prograquiz.ui.components.AppButton

@Composable
fun ResultScreen(

    score: Int,

    correct: Int,

    incorrect: Int,

    onPlayAgain: () -> Unit,

    onHome: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                text = "Resultado Final",
                style =
                    MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Puntaje: $score"
            )

            Text(
                text = "Correctas: $correct"
            )

            Text(
                text = "Incorrectas: $incorrect"
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            AppButton(
                text = "Jugar Nuevamente"
            ) {
                onPlayAgain()
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            AppButton(
                text = "Volver al Inicio"
            ) {
                onHome()
            }
        }
    }
}