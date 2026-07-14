package com.programacion.prograquiz.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programacion.prograquiz.model.DifficultyLevel
import com.programacion.prograquiz.ui.components.DifficultyBadge
import com.programacion.prograquiz.ui.components.PQOutlineButton
import com.programacion.prograquiz.ui.components.PQPrimaryButton
import com.programacion.prograquiz.ui.components.ScoreRing
import com.programacion.prograquiz.ui.theme.*

@Composable
fun ResultScreen(
    score: Int, correct: Int, total: Int, level: DifficultyLevel,
    onPlayAgain: () -> Unit, onGoHome: () -> Unit
) {
    val wrong   = total - correct
    val message = when {
        score == 100 -> "¡Perfecto! 🏆"
        score >= 80  -> "¡Muy bien! 🎯"
        score >= 60  -> "¡Bien! Sigue practicando 💪"
        else         -> "Sigue intentándolo 📚"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))

        Text(
            text      = "Resultado",
            fontSize  = 22.sp,
            fontWeight = FontWeight.Bold,
            color     = TextPrimary
        )
        Spacer(Modifier.height(8.dp))
        DifficultyBadge(level)
        Spacer(Modifier.height(24.dp))

        ScoreRing(score = score, size = 120)
        Spacer(Modifier.height(16.dp))

        Text(message, fontSize = 17.sp, color = TextPrimary, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
        Spacer(Modifier.height(28.dp))

        // Resumen simple
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape    = RoundedCornerShape(12.dp),
            colors   = CardDefaults.cardColors(containerColor = CardDark)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ResumenRow("Correctas",   "$correct",  CorrectGreen)
                HorizontalDivider(color = DividerColor)
                ResumenRow("Incorrectas", "$wrong",    WrongRed)
                HorizontalDivider(color = DividerColor)
                ResumenRow("Puntaje",     "$score / 100", PrimaryBlue)
            }
        }

        Spacer(Modifier.height(32.dp))

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
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun ResumenRow(label: String, value: String, valueColor: Color) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = TextSecondary, fontSize = 14.sp)
        Text(value, color = valueColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}
