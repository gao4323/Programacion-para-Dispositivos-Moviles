package com.example.prograquiz.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
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
import com.example.prograquiz.ui.components.*
import com.example.prograquiz.ui.theme.*
import com.example.prograquiz.viewmodel.QuizViewModel

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

    // When timer expires: auto-confirm as wrong then route
    LaunchedEffect(timerExpired) {
        if (!timerExpired) return@LaunchedEffect
        val quiz = state ?: return@LaunchedEffect
        val isCorrect = viewModel.confirmAnswer()   // locks the answer
        if (quiz.isLastQuestion) {
            val (score, correct, total) = viewModel.getFinalStats()
            onQuizFinished(score, correct, total, viewModel.getCurrentLevel().name)
        } else {
            onAnswerConfirmed(isCorrect, quiz.currentIndex)
        }
    }

    if (state == null) {
        Box(
            Modifier.fillMaxSize().background(BackgroundDark),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator(color = PrimaryBlue) }
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
                    Row(
                        modifier = Modifier.padding(end = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Timer chip
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = timerColor.copy(alpha = 0.15f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Timer, null,
                                    tint     = timerColor,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    "$timerSeconds",
                                    color      = timerColor,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize   = 14.sp
                                )
                            }
                        }
                        // Correct counter
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, null, tint = CorrectGreen, modifier = Modifier.size(15.dp))
                            Spacer(Modifier.width(3.dp))
                            Text("${quiz.correctCount}", color = CorrectGreen, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        // Wrong counter
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Cancel, null, tint = WrongRed, modifier = Modifier.size(15.dp))
                            Spacer(Modifier.width(3.dp))
                            Text("${quiz.wrongCount}", color = WrongRed, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            )
        },
        containerColor = BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(14.dp))

            // Quiz progress bar
            QuizProgressBar(
                progress = quiz.progress,
                current  = quiz.currentIndex + 1,
                total    = quiz.questions.size
            )
            Spacer(Modifier.height(6.dp))

            // Timer bar (thin, below quiz progress)
            LinearProgressIndicator(
                progress   = { timerProgress },
                modifier   = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                color      = timerColor,
                trackColor = DividerColor
            )

            Spacer(Modifier.height(16.dp))

            // Level badge + urgency warning
            Row(verticalAlignment = Alignment.CenterVertically) {
                DifficultyBadge(question.level)
                Spacer(Modifier.width(8.dp))
                AnimatedVisibility(visible = timerSeconds <= 8 && !quiz.answered) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = WrongRed.copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Warning, null, tint = WrongRed, modifier = Modifier.size(12.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("¡Apúrate!", color = WrongRed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // Question card
            PQCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Pregunta ${quiz.currentIndex + 1}",
                    color      = TextHint,
                    fontSize   = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text       = question.text,
                    color      = TextPrimary,
                    style      = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp,
                    fontFamily = if (question.text.contains("\n")) FontFamily.Monospace else FontFamily.Default
                )
            }

            Spacer(Modifier.height(16.dp))

            // Answer options — animate slide when question changes
            AnimatedContent(
                targetState   = quiz.currentIndex,
                transitionSpec = {
                    (slideInHorizontally(tween(280)) { it } + fadeIn(tween(280))) togetherWith
                    (slideOutHorizontally(tween(220)) { -it } + fadeOut(tween(180)))
                },
                label = "options"
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
            }

            Spacer(Modifier.height(24.dp))

            // Action button — animated transition between states
            AnimatedContent(
                targetState   = quiz.answered,
                transitionSpec = { fadeIn(tween(200)) togetherWith fadeOut(tween(150)) },
                label         = "actionBtn"
            ) { answered ->
                if (!answered) {
                    PQPrimaryButton(
                        text     = "Confirmar respuesta",
                        onClick  = {
                            val isCorrect = viewModel.confirmAnswer()
                            if (quiz.isLastQuestion) {
                                val (score, correct, total) = viewModel.getFinalStats()
                                onQuizFinished(score, correct, total, viewModel.getCurrentLevel().name)
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
                        text     = if (quiz.isLastQuestion) "Ver resultado final" else "Siguiente pregunta",
                        onClick  = {
                            if (quiz.isLastQuestion) {
                                val (score, correct, total) = viewModel.getFinalStats()
                                onQuizFinished(score, correct, total, viewModel.getCurrentLevel().name)
                            } else {
                                val isCorrect = (quiz.selectedOptionIndex == question.correctIndex)
                                onAnswerConfirmed(isCorrect, quiz.currentIndex)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        icon     = if (quiz.isLastQuestion) Icons.Default.EmojiEvents else Icons.Default.ArrowForward
                    )
                }
            }

            Spacer(Modifier.height(28.dp))
        }
    }
}
