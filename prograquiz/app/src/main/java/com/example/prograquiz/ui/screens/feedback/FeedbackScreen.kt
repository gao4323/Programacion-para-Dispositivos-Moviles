package com.example.prograquiz.ui.screens.feedback

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.prograquiz.ui.components.AppButton

@Composable
fun FeedbackScreen(

    isCorrect: Boolean,

    explanation: String,

    onContinue: () -> Unit
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
                text =
                    if (isCorrect)
                        "Correcto"
                    else
                        "Incorrecto",
                style =
                    MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = explanation,
                style =
                    MaterialTheme.typography.bodyLarge
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            AppButton(
                text = "Continuar"
            ) {
                onContinue()
            }
        }
    }
}