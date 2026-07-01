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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programacion.prograquiz.ui.components.PQPrimaryButton
import com.programacion.prograquiz.ui.theme.*
import com.programacion.prograquiz.viewmodel.QuizViewModel

@Composable
fun FeedbackScreen(
    isCorrect: Boolean,
    questionIndex: Int,
    viewModel: QuizViewModel,
    onNext: () -> Unit,
    onFinish: (score: Int, correct: Int, total: Int, level: String) -> Unit
) {
    val state    by viewModel.quizState.collectAsState()
    val question = state?.questions?.getOrNull(questionIndex) ?: return
    val isLast   = state?.isLastQuestion ?: false

    val color   = if (isCorrect) CorrectGreen else WrongRed
    val icon    = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel
    val titulo  = if (isCorrect) "¡Correcto!" else "Incorrecto"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))

        Icon(icon, null, tint = color, modifier = Modifier.size(64.dp))
        Spacer(Modifier.height(12.dp))
        Text(titulo, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = color)
        Spacer(Modifier.height(24.dp))

        // Explicación
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape    = RoundedCornerShape(12.dp),
            colors   = CardDefaults.cardColors(containerColor = CardDark)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Lightbulb, null, tint = GoldColor, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Explicación", color = GoldColor, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                }
                Spacer(Modifier.height(8.dp))
                Text(question.explanation, color = TextPrimary, fontSize = 14.sp, lineHeight = 20.sp)
            }
        }

        // Respuesta correcta (cuando falla)
        if (!isCorrect) {
            Spacer(Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(12.dp),
                colors   = CardDefaults.cardColors(containerColor = CorrectGreen.copy(alpha = 0.1f))
            ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.CheckCircleOutline, null, tint = CorrectGreen, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text("Respuesta correcta:", color = CorrectGreen, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text(question.options[question.correctIndex], color = TextPrimary, fontSize = 13.sp)
                    }
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        PQPrimaryButton(
            text     = if (isLast) "Ver resultado" else "Siguiente",
            onClick  = {
                if (isLast) {
                    val (s, c, t) = viewModel.getFinalStats()
                    onFinish(s, c, t, viewModel.getCurrentLevel().name)
                } else {
                    onNext()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            icon     = if (isLast) Icons.Default.EmojiEvents else Icons.Default.ArrowForward
        )
        Spacer(Modifier.height(32.dp))
    }
}
