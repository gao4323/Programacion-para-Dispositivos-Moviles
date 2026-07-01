package com.example.prograquiz.ui.screens
import com.example.prograquiz.data.mock.MockData

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prograquiz.model.DifficultyLevel
import com.example.prograquiz.ui.components.*
import com.example.prograquiz.ui.theme.*
import com.example.prograquiz.viewmodel.SessionViewModel

@Composable
fun ProfileScreen(
    sessionViewModel: SessionViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    val user    by sessionViewModel.currentUser.collectAsState()
    val history by sessionViewModel.history.collectAsState()

    val displayUser = user ?: MockData.currentUser
    val accuracy = if (displayUser.totalQuestions > 0)
        (displayUser.totalCorrect * 100 / displayUser.totalQuestions) else 0

    Scaffold(
        topBar = { PQTopBar(title = "Mi Perfil", onBack = onNavigateBack) },
        containerColor = BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // ── Banner ─────────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(listOf(Color(0xFF0D2850), PrimaryBlueDark, SecondaryPurple))
                    )
                    .padding(28.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AvatarCircle(displayUser.avatarInitials, size = 80, backgroundColor = AccentCyan)
                    Spacer(Modifier.height(12.dp))
                    Text(displayUser.username, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(displayUser.email, color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
                    Spacer(Modifier.height(10.dp))
                    DifficultyBadge(displayUser.favoriteLevel)
                }
            }

            Spacer(Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                // ── Stats grid ──────────────────────────────────────────────────
                SectionHeader("Estadísticas generales")
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        value     = "${displayUser.bestScore}",
                        label     = "Mejor pts.",
                        icon      = Icons.Default.EmojiEvents,
                        iconColor = GoldColor,
                        modifier  = Modifier.weight(1f)
                    )
                    StatCard(
                        value     = "${displayUser.totalGames}",
                        label     = "Partidas",
                        icon      = Icons.Default.SportsEsports,
                        iconColor = PrimaryBlue,
                        modifier  = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        value     = "$accuracy%",
                        label     = "Precisión",
                        icon      = Icons.Default.TrendingUp,
                        iconColor = AccentCyan,
                        modifier  = Modifier.weight(1f)
                    )
                    StatCard(
                        value     = "${displayUser.totalCorrect}",
                        label     = "Correctas",
                        icon      = Icons.Default.CheckCircle,
                        iconColor = CorrectGreen,
                        modifier  = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(26.dp))

                // ── Level breakdown ────────────────────────────────────────────
                SectionHeader("Desempeño por nivel")
                Spacer(Modifier.height(12.dp))

                DifficultyLevel.entries.forEach { level ->
                    val gamesForLevel = history.filter { it.level == level }
                    val avg = if (gamesForLevel.isEmpty()) 0
                              else gamesForLevel.map { it.score }.average().toInt()
                    val levelColor = when (level) {
                        DifficultyLevel.BASICO      -> LevelBasic
                        DifficultyLevel.INTERMEDIO  -> LevelIntermediate
                        DifficultyLevel.AVANZADO    -> LevelAdvanced
                    }

                    PQCard(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            DifficultyBadge(level)
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "${gamesForLevel.size} partidas",
                                        color    = TextSecondary,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        "$avg pts prom.",
                                        color      = levelColor,
                                        fontSize   = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(Modifier.height(6.dp))
                                LinearProgressIndicator(
                                    progress   = { avg / 100f },
                                    modifier   = Modifier.fillMaxWidth().height(6.dp),
                                    color      = levelColor,
                                    trackColor = CardDarker
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                OutlinedButton(
                    onClick  = onNavigateToHistory,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape    = RoundedCornerShape(14.dp),
                    colors   = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                    border   = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
                ) {
                    Icon(Icons.Default.History, null, tint = PrimaryBlue, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Ver historial completo", fontWeight = FontWeight.Medium)
                }

                Spacer(Modifier.height(36.dp))
            }
        }
    }
}
