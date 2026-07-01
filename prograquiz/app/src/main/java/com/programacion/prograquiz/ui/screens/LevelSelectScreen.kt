package com.programacion.prograquiz.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programacion.prograquiz.model.DifficultyLevel
import com.programacion.prograquiz.ui.components.PQTopBar
import com.programacion.prograquiz.ui.theme.*

@Composable
fun LevelSelectScreen(onLevelSelected: (DifficultyLevel) -> Unit, onNavigateBack: () -> Unit) {
    Scaffold(
        topBar         = { PQTopBar(title = "Seleccionar nivel", onBack = onNavigateBack) },
        containerColor = BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("¿Qué nivel quieres practicar?", color = TextSecondary, fontSize = 14.sp)
            Spacer(Modifier.height(4.dp))

            LevelCard(DifficultyLevel.BASICO,     "Variables, condiciones y bucles",    LevelBasic)      { onLevelSelected(DifficultyLevel.BASICO) }
            LevelCard(DifficultyLevel.INTERMEDIO, "OOP, recursividad y estructuras",    LevelIntermediate) { onLevelSelected(DifficultyLevel.INTERMEDIO) }
            LevelCard(DifficultyLevel.AVANZADO,   "Algoritmos, patrones y concurrencia", LevelAdvanced)  { onLevelSelected(DifficultyLevel.AVANZADO) }

            Spacer(Modifier.height(8.dp))
            Card(
                shape  = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = CardDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, null, tint = AccentCyan, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Cada nivel tiene 5 preguntas. Tienes 30 segundos por pregunta.", color = TextSecondary, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun LevelCard(level: DifficultyLevel, description: String, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(level.label, color = color, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(2.dp))
                Text(description, color = TextSecondary, fontSize = 12.sp)
            }
            Icon(Icons.Default.ArrowForwardIos, null, tint = color, modifier = Modifier.size(16.dp))
        }
    }
}
