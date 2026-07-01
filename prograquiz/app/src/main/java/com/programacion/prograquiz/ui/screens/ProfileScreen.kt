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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programacion.prograquiz.data.mock.MockData
import com.programacion.prograquiz.ui.components.AvatarCircle
import com.programacion.prograquiz.ui.components.PQTopBar
import com.programacion.prograquiz.ui.theme.*
import com.programacion.prograquiz.viewmodel.SessionViewModel

@Composable
fun ProfileScreen(
    sessionViewModel: SessionViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    val user        by sessionViewModel.currentUser.collectAsState()
    val displayUser = user ?: MockData.currentUser
    val accuracy    = if (displayUser.totalQuestions > 0)
        (displayUser.totalCorrect * 100 / displayUser.totalQuestions) else 0

    Scaffold(
        topBar         = { PQTopBar(title = "Mi Perfil", onBack = onNavigateBack) },
        containerColor = BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            // Avatar y datos básicos
            AvatarCircle(displayUser.avatarInitials, size = 72, backgroundColor = PrimaryBlue)
            Spacer(Modifier.height(12.dp))
            Text(displayUser.username, color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(displayUser.email, color = TextSecondary, fontSize = 13.sp)

            Spacer(Modifier.height(28.dp))

            // Estadísticas en cards simples
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(12.dp),
                colors   = CardDefaults.cardColors(containerColor = CardDark)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatRow(Icons.Default.EmojiEvents, "Mejor puntaje",  "${displayUser.bestScore} pts",  GoldColor)
                    HorizontalDivider(color = DividerColor)
                    StatRow(Icons.Default.SportsEsports, "Partidas jugadas", "${displayUser.totalGames}", PrimaryBlue)
                    HorizontalDivider(color = DividerColor)
                    StatRow(Icons.Default.CheckCircle, "Respuestas correctas", "${displayUser.totalCorrect}", CorrectGreen)
                    HorizontalDivider(color = DividerColor)
                    StatRow(Icons.Default.TrendingUp, "Precisión", "$accuracy%", AccentCyan)
                }
            }

            Spacer(Modifier.height(20.dp))

            OutlinedButton(
                onClick  = onNavigateToHistory,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape    = RoundedCornerShape(10.dp),
                colors   = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                border   = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
            ) {
                Icon(Icons.Default.History, null, tint = PrimaryBlue, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Ver historial", fontWeight = FontWeight.Medium)
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun StatRow(icon: ImageVector, label: String, value: String, iconColor: Color) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = iconColor, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(10.dp))
        Text(label, color = TextSecondary, fontSize = 14.sp, modifier = Modifier.weight(1f))
        Text(value, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}
