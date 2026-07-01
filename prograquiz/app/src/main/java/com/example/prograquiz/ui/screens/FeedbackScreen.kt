package com.example.prograquiz.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prograquiz.ui.components.*
import com.example.prograquiz.ui.theme.*
import com.example.prograquiz.viewmodel.QuizViewModel

@Composable
fun FeedbackScreen(
    isCorrect: Boolean,
    questionIndex: Int,
    viewModel: QuizViewModel,
    /** Called when the user presses "Continuar" on a non-final question */
    onNext: () -> Unit,
    /** Called when the user presses "Ver resultado" on the final question */
    onFinish: (score: Int, correct: Int, total: Int, level: String) -> Unit
) {
    val state    by viewModel.quizState.collectAsState()
    val question = state?.questions?.getOrNull(questionIndex) ?: return
    val isLast   = state?.isLastQuestion ?: false

    // Bounce-in animation for the result icon
    val animScale by animateFloatAsState(
        targetValue    = 1f,
        animationSpec  = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label          = "iconScale"
    )

    val primaryColor = if (isCorrect) CorrectGreen else WrongRed
    val bgGradient   = if (isCorrect)
        listOf(Color(0xFF0D2218), BackgroundDark)
    else
        listOf(Color(0xFF2A0D0D), BackgroundDark)

    Scaffold(containerColor = BackgroundDark) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Brush.verticalGradient(bgGradient))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(36.dp))

            // ── Result icon ────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(108.dp)
                    .clip(CircleShape)
                    .background(primaryColor.copy(alpha = 0.13f))
                    .scale(animScale),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                    contentDescription = null,
                    tint     = primaryColor,
                    modifier = Modifier.size(68.dp)
                )
            }

            Spacer(Modifier.height(18.dp))

            Text(
                text       = if (isCorrect) "¡Correcto! 🎉" else "Incorrecto 😕",
                fontSize   = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color      = primaryColor
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text      = if (isCorrect) "¡Excelente razonamiento!" else "Revisa la explicación y sigue adelante.",
                color     = TextSecondary,
                fontSize  = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            // ── Explanation card ───────────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(20.dp),
                colors   = CardDefaults.cardColors(containerColor = CardDark)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lightbulb, null, tint = GoldColor, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Explicación", color = GoldColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text      = question.explanation,
                        color     = TextPrimary,
                        fontSize  = 15.sp,
                        lineHeight = 22.sp
                    )
                }
            }

            // ── Correct answer highlight (when wrong) ─────────────────────────
            if (!isCorrect) {
                Spacer(Modifier.height(14.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape    = RoundedCornerShape(16.dp),
                    colors   = CardDefaults.cardColors(containerColor = CorrectGreen.copy(alpha = 0.1f))
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                        Icon(
                            Icons.Default.CheckCircleOutline, null,
                            tint     = CorrectGreen,
                            modifier = Modifier.size(20.dp).padding(top = 2.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text("Respuesta correcta:", color = CorrectGreen, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Spacer(Modifier.height(4.dp))
                            Text(question.options[question.correctIndex], color = TextPrimary, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // ── Action button ──────────────────────────────────────────────────
            if (isLast) {
                PQPrimaryButton(
                    text     = "Ver resultado final 🏆",
                    onClick  = {
                        val (score, correct, total) = viewModel.getFinalStats()
                        onFinish(score, correct, total, viewModel.getCurrentLevel().name)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    icon     = Icons.Default.EmojiEvents
                )
            } else {
                PQPrimaryButton(
                    text     = "Siguiente pregunta",
                    onClick  = onNext,
                    modifier = Modifier.fillMaxWidth(),
                    icon     = Icons.Default.ArrowForward
                )
            }

            Spacer(Modifier.height(36.dp))
        }
    }
}
