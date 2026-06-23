package com.example.prograquiz.ui.screens.quiz

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.prograquiz.data.mock.MockData
import com.example.prograquiz.model.Question
import com.example.prograquiz.ui.components.AppButton
import com.example.prograquiz.ui.components.AppTopBar

@Composable
fun QuizScreen(
    onAnswer: (
        Question,
        Int
    ) -> Unit
) {

    val question = MockData.questions.first()

    var selectedOption by remember {
        mutableIntStateOf(-1)
    }

    Scaffold(

        topBar = {
            AppTopBar("Quiz")
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "Pregunta 1 de ${MockData.questions.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            LinearProgressIndicator(
                progress = { 0.33f },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = question.question,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            question.options.forEachIndexed { index, option ->

                Card(
                    onClick = {
                        selectedOption = index
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = option,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            AppButton(
                text = "Responder"
            ) {

                if (selectedOption != -1) {

                    onAnswer(
                        question,
                        selectedOption
                    )
                }
            }
        }
    }
}