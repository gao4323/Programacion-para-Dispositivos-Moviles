package com.example.prograquiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prograquiz.model.DifficultyLevel
import com.example.prograquiz.ui.components.PQTopBar
import com.example.prograquiz.ui.theme.*

@Composable
fun LevelSelectScreen(
    onLevelSelected: (DifficultyLevel) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = { PQTopBar(title = "Seleccionar nivel", onBack = onNavigateBack) },
        containerColor = BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text      = "¿Qué nivel quieres practicar hoy?",
                color     = TextSecondary,
                fontSize  = 15.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(28.dp))

            // Level cards
            BigLevelCard(
                level       = DifficultyLevel.BASICO,
                subtitle    = "5 preguntas · ~5 min",
                description = "Tipos de datos, condicionales, bucles básicos y operadores.",
                emoji       = "🟢",
                gradient    = listOf(Color(0xFF1B5E20), Color(0xFF2E7D32)),
                accentColor = LevelBasic,
                onClick     = { onLevelSelected(DifficultyLevel.BASICO) }
            )
            Spacer(Modifier.height(16.dp))

            BigLevelCard(
                level       = DifficultyLevel.INTERMEDIO,
                subtitle    = "5 preguntas · ~7 min",
                description = "OOP, recursividad, estructuras de datos, complejidad básica.",
                emoji       = "🟡",
                gradient    = listOf(Color(0xFFE65100), Color(0xFFBF360C)),
                accentColor = LevelIntermediate,
                onClick     = { onLevelSelected(DifficultyLevel.INTERMEDIO) }
            )
            Spacer(Modifier.height(16.dp))

            BigLevelCard(
                level       = DifficultyLevel.AVANZADO,
                subtitle    = "5 preguntas · ~10 min",
                description = "Algoritmos avanzados, patrones de diseño, concurrencia.",
                emoji       = "🔴",
                gradient    = listOf(Color(0xFFB71C1C), Color(0xFF880E4F)),
                accentColor = LevelAdvanced,
                onClick     = { onLevelSelected(DifficultyLevel.AVANZADO) }
            )

            Spacer(Modifier.height(32.dp))

            // Info card
            Card(
                shape  = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Info,
                        null,
                        tint = AccentCyan,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text  = "Cada nivel tiene 5 preguntas. Recibirás retroalimentación inmediata tras cada respuesta.",
                        color = TextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun BigLevelCard(
    level: DifficultyLevel,
    subtitle: String,
    description: String,
    emoji: String,
    gradient: List<Color>,
    accentColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier  = Modifier.fillMaxWidth().clickable { onClick() },
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(gradient))
                .padding(20.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(emoji, fontSize = 32.sp)
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text       = level.label,
                            color      = Color.White,
                            fontSize   = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(subtitle, color = Color.White.copy(alpha = 0.75f), fontSize = 12.sp)
                    }
                    Icon(
                        Icons.Default.ArrowForwardIos,
                        null,
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    text     = description,
                    color    = Color.White.copy(alpha = 0.85f),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}
