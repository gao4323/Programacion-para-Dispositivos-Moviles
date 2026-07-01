package com.example.prograquiz.ui.screens

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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prograquiz.model.DifficultyLevel
import com.example.prograquiz.ui.components.*
import com.example.prograquiz.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun ResultScreen(
    score: Int,
    correct: Int,
    total: Int,
    level: DifficultyLevel,
    onPlayAgain: () -> Unit,
    onGoHome: () -> Unit
) {
    val wrong   = total - correct
    val accuracy = if (total > 0) (correct * 100 / total) else 0

    val (emoji, message, grade) = when {
        score == 100 -> Triple("🏆", "¡Puntaje perfecto! Eres un crack", "S")
        score >= 80  -> Triple("🎯", "¡Excelente desempeño! Sigue así", "A")
        score >= 60  -> Triple("💪", "¡Buen trabajo! Puedes mejorar más", "B")
        score >= 40  -> Triple("📚", "Sigue practicando, vas bien", "C")
        else         -> Triple("🔄", "No te rindas, intenta de nuevo", "D")
    }

    val gradeColor = when (grade) {
        "S"  -> GoldColor
        "A"  -> CorrectGreen
        "B"  -> AccentCyan
        "C"  -> LevelIntermediate
        else -> WrongRed
    }

    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope             = rememberCoroutineScope()

    // Entrance scale animation for the ring
    val scale by animateFloatAsState(
        targetValue   = 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label         = "ring"
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Brush.verticalGradient(listOf(Color(0xFF0A1628), BackgroundDark)))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(28.dp))

            // ── Grade & emoji ──────────────────────────────────────────────────
            Text(emoji, fontSize = 52.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                "Quiz completado",
                color      = TextSecondary,
                fontSize   = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                message,
                color      = TextPrimary,
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign  = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))
            DifficultyBadge(level)
            Spacer(Modifier.height(24.dp))

            // ── Score ring + grade ─────────────────────────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                ScoreRing(score = score, size = 130)
                Spacer(Modifier.width(28.dp))
                // Grade circle
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .scale(scale)
                        .background(gradeColor.copy(alpha = 0.12f), shape = RoundedCornerShape(50)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            grade,
                            color      = gradeColor,
                            fontSize   = 36.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text("Nota", color = gradeColor.copy(alpha = 0.7f), fontSize = 11.sp)
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // ── Stats row ──────────────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    value     = "$correct",
                    label     = "Correctas",
                    icon      = Icons.Default.CheckCircle,
                    iconColor = CorrectGreen,
                    modifier  = Modifier.weight(1f)
                )
                StatCard(
                    value     = "$wrong",
                    label     = "Incorrectas",
                    icon      = Icons.Default.Cancel,
                    iconColor = WrongRed,
                    modifier  = Modifier.weight(1f)
                )
                StatCard(
                    value     = "$accuracy%",
                    label     = "Precisión",
                    icon      = Icons.Default.TrendingUp,
                    iconColor = AccentCyan,
                    modifier  = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(20.dp))

            // ── Performance detail ─────────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(16.dp),
                colors   = CardDefaults.cardColors(containerColor = CardDark)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Resumen",
                        color      = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 15.sp
                    )
                    Spacer(Modifier.height(14.dp))
                    ResultRow("Puntaje",   "$score / 100",   PrimaryBlue)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = DividerColor)
                    ResultRow("Nivel",     level.label,      when (level) {
                        DifficultyLevel.BASICO      -> LevelBasic
                        DifficultyLevel.INTERMEDIO  -> LevelIntermediate
                        DifficultyLevel.AVANZADO    -> LevelAdvanced
                    })
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = DividerColor)
                    ResultRow("Preguntas", "$total respondidas", TextSecondary)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = DividerColor)
                    ResultRow("Calificación", grade, gradeColor)
                }
            }

            Spacer(Modifier.height(28.dp))

            // ── Actions ────────────────────────────────────────────────────────
            PQPrimaryButton(
                text     = "Jugar de nuevo",
                onClick  = onPlayAgain,
                modifier = Modifier.fillMaxWidth(),
                icon     = Icons.Default.Replay
            )
            Spacer(Modifier.height(10.dp))
            PQOutlineButton(
                text     = "Volver al inicio",
                onClick  = onGoHome,
                modifier = Modifier.fillMaxWidth(),
                icon     = Icons.Default.Home
            )
            Spacer(Modifier.height(10.dp))
            // Share mock
            OutlinedButton(
                onClick  = {
                    scope.launch {
                        snackbarHostState.showSnackbar("¡Resultado compartido! (simulado)")
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape    = RoundedCornerShape(14.dp),
                border   = androidx.compose.foundation.BorderStroke(1.dp, SecondaryPurple),
                colors   = ButtonDefaults.outlinedButtonColors(contentColor = SecondaryPurple)
            ) {
                Icon(Icons.Default.Share, null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Compartir resultado", fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(36.dp))
        }
    }
}

@Composable
private fun ResultRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = TextSecondary, fontSize = 14.sp)
        Text(value, color = valueColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}
