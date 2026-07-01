package com.programacion.prograquiz.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programacion.prograquiz.ui.components.*
import com.programacion.prograquiz.ui.theme.*
import com.programacion.prograquiz.viewmodel.QuizViewModel

@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onAnswerConfirmed: (isCorrect: Boolean, questionIndex: Int) -> Unit,
    onQuizFinished: (score: Int, correct: Int, total: Int, level: String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val state         by viewModel.quizState.collectAsState()
    val timerSeconds  by viewModel.timerSeconds.collectAsState()
    val timerProgress by viewModel.timerProgress.collectAsState()
    val timerExpired  by viewModel.timerExpired.collectAsState()

    LaunchedEffect(timerExpired) {
        if (!timerExpired) return@LaunchedEffect
        val quiz = state ?: return@LaunchedEffect
        val isCorrect = viewModel.confirmAnswer()
        if (quiz.isLastQuestion) {
            val (score, correct, total) = viewModel.getFinalStats()
            onQuizFinished(score, correct, total, viewModel.getCurrentLevel().name)
        } else {
            onAnswerConfirmed(isCorrect, quiz.currentIndex)
        }
    }

    if (state == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = PrimaryBlue)
        }
        return
    }

    val quiz     = state!!
    val question = quiz.currentQuestion
    val timerColor = when {
        timerSeconds > 15 -> CorrectGreen
        timerSeconds > 8  -> LevelIntermediate
        else              -> WrongRed
    }

    Scaffold(
        topBar = {
            PQTopBar(
                title  = "Pregunta ${quiz.currentIndex + 1} / ${quiz.questions.size}",
                onBack = onNavigateBack,
                actions = {
                    // Timer simple
                    Text(
                        "$timerSeconds s",
                        color      = timerColor,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 14.sp,
                        modifier   = Modifier.padding(end = 16.dp)
                    )
                }
            )
        },
        containerColor = BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))

            // Barra de progreso del quiz
            LinearProgressIndicator(
                progress   = { quiz.progress },
                modifier   = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color      = PrimaryBlue,
                trackColor = DividerColor
            )

            Spacer(Modifier.height(4.dp))

            // Barra del timer
            LinearProgressIndicator(
                progress   = { timerProgress },
                modifier   = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                color      = timerColor,
                trackColor = DividerColor
            )

            Spacer(Modifier.height(16.dp))

            DifficultyBadge(question.level)
            Spacer(Modifier.height(12.dp))

            // Pregunta
            PQCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text       = question.text,
                    color      = TextPrimary,
                    style      = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp,
                    fontFamily = if (question.text.contains("\n")) FontFamily.Monospace else FontFamily.Default
                )
            }

            Spacer(Modifier.height(14.dp))

            // Opciones de respuesta
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                question.options.forEachIndexed { index, option ->
                    AnswerOption(
                        text       = option,
                        index      = index,
                        isSelected = quiz.selectedOptionIndex == index,
                        isAnswered = quiz.answered,
                        isCorrect  = index == question.correctIndex,
                        onClick    = { viewModel.selectOption(index) }
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            if (!quiz.answered) {
                PQPrimaryButton(
                    text     = "Confirmar",
                    onClick  = {
                        val isCorrect = viewModel.confirmAnswer()
                        if (quiz.isLastQuestion) {
                            val (s, c, t) = viewModel.getFinalStats()
                            onQuizFinished(s, c, t, viewModel.getCurrentLevel().name)
                        } else {
                            onAnswerConfirmed(isCorrect, quiz.currentIndex)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled  = quiz.selectedOptionIndex != null,
                    icon     = Icons.Default.Check
                )
            } else {
                PQPrimaryButton(
                    text     = if (quiz.isLastQuestion) "Ver resultado" else "Siguiente",
                    onClick  = {
                        if (quiz.isLastQuestion) {
                            val (s, c, t) = viewModel.getFinalStats()
                            onQuizFinished(s, c, t, viewModel.getCurrentLevel().name)
                        } else {
                            onAnswerConfirmed(
                                quiz.selectedOptionIndex == question.correctIndex,
                                quiz.currentIndex
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    icon     = if (quiz.isLastQuestion) Icons.Default.EmojiEvents else Icons.Default.ArrowForward
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
