package com.example.prograquiz.ui.screens
import com.example.prograquiz.data.mock.MockData

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prograquiz.model.DifficultyLevel
import com.example.prograquiz.ui.components.*
import com.example.prograquiz.ui.theme.*
import com.example.prograquiz.viewmodel.SessionViewModel

@Composable
fun HomeScreen(
    sessionViewModel: SessionViewModel,
    onNavigateToLevelSelect: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val user    by sessionViewModel.currentUser.collectAsState()
    val history by sessionViewModel.history.collectAsState()

    val displayUser = user ?: MockData.currentUser

    Scaffold(
        containerColor = BackgroundDark,
        bottomBar = {
            NavigationBar(
                containerColor = SurfaceDark,
                tonalElevation = 8.dp
            ) {
                listOf(
                    Triple(Icons.Default.Home,        "Inicio",    {}),
                    Triple(Icons.Default.Leaderboard, "Ranking",   onNavigateToRanking),
                    Triple(Icons.Default.History,     "Historial", onNavigateToHistory),
                    Triple(Icons.Default.Person,      "Perfil",    onNavigateToProfile)
                ).forEachIndexed { idx, (icon, label, action) ->
                    NavigationBarItem(
                        selected = idx == 0,
                        onClick  = action,
                        icon     = { Icon(icon, null) },
                        label    = { Text(label, fontSize = 11.sp) },
                        colors   = NavigationBarItemDefaults.colors(
                            selectedIconColor   = PrimaryBlue,
                            selectedTextColor   = PrimaryBlue,
                            indicatorColor      = PrimaryBlue.copy(alpha = 0.14f),
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        )
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // ── Header ─────────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(listOf(Color(0xFF0D2850), PrimaryBlueDark, SecondaryPurple))
                    )
                    .padding(horizontal = 24.dp, vertical = 28.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Hola,",
                                color    = Color.White.copy(alpha = 0.75f),
                                fontSize = 14.sp
                            )
                            Text(
                                displayUser.username,
                                color      = Color.White,
                                fontSize   = 22.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AvatarCircle(
                                displayUser.avatarInitials,
                                size = 46,
                                backgroundColor = AccentCyan
                            )
                            Spacer(Modifier.width(10.dp))
                            IconButton(onClick = onNavigateToSettings) {
                                Icon(Icons.Default.Settings, null, tint = Color.White.copy(alpha = 0.8f))
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // Stats row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        HomeStatChip("🏆", "${displayUser.bestScore}", "Mejor")
                        HomeStatChip("🎮", "${displayUser.totalGames}", "Partidas")
                        HomeStatChip("✅", "${displayUser.totalCorrect}", "Correctas")
                        HomeStatChip("📊", run {
                            val acc = if (displayUser.totalQuestions > 0)
                                (displayUser.totalCorrect * 100 / displayUser.totalQuestions) else 0
                            "$acc%"
                        }, "Precisión")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                // ── Play CTA card ──────────────────────────────────────────────
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Brush.linearGradient(listOf(PrimaryBlue, AccentCyan)))
                        .clickable { onNavigateToLevelSelect() }
                        .padding(horizontal = 24.dp, vertical = 22.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                "¡Jugar ahora!",
                                color      = Color.White,
                                fontSize   = 22.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Elige un nivel y empieza",
                                color    = Color.White.copy(alpha = 0.85f),
                                fontSize = 13.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                null,
                                tint     = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(28.dp))

                // ── Levels ─────────────────────────────────────────────────────
                SectionHeader("Niveles de dificultad")
                Spacer(Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    LevelRowCard(
                        level       = DifficultyLevel.BASICO,
                        description = "Condicionales, bucles y tipos básicos",
                        icon        = Icons.Default.LooksOne,
                        questions   = 5,
                        onClick     = onNavigateToLevelSelect
                    )
                    LevelRowCard(
                        level       = DifficultyLevel.INTERMEDIO,
                        description = "OOP, recursividad y estructuras",
                        icon        = Icons.Default.LooksTwo,
                        questions   = 5,
                        onClick     = onNavigateToLevelSelect
                    )
                    LevelRowCard(
                        level       = DifficultyLevel.AVANZADO,
                        description = "Algoritmos, patrones y concurrencia",
                        icon        = Icons.Default.Looks3,
                        questions   = 5,
                        onClick     = onNavigateToLevelSelect
                    )
                }

                Spacer(Modifier.height(28.dp))

                // ── Quick access ───────────────────────────────────────────────
                SectionHeader("Acceso rápido")
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickAccessCard(
                        icon    = Icons.Default.Leaderboard,
                        label   = "Ranking",
                        color   = GoldColor,
                        onClick = onNavigateToRanking,
                        modifier = Modifier.weight(1f)
                    )
                    QuickAccessCard(
                        icon    = Icons.Default.History,
                        label   = "Historial",
                        color   = AccentCyan,
                        onClick = onNavigateToHistory,
                        modifier = Modifier.weight(1f)
                    )
                    QuickAccessCard(
                        icon    = Icons.Default.Person,
                        label   = "Perfil",
                        color   = SecondaryPurple,
                        onClick = onNavigateToProfile,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(28.dp))

                // ── Recent activity ────────────────────────────────────────────
                SectionHeader(
                    title    = "Actividad reciente",
                    action   = "Ver todo",
                    onAction = onNavigateToHistory
                )
                Spacer(Modifier.height(12.dp))

                val recent = history.take(3)
                if (recent.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape    = RoundedCornerShape(14.dp),
                        colors   = CardDefaults.cardColors(containerColor = CardDark)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.SportsEsports, null, tint = TextHint, modifier = Modifier.size(36.dp))
                                Spacer(Modifier.height(8.dp))
                                Text("Aún no has jugado ninguna partida", color = TextSecondary, fontSize = 13.sp)
                            }
                        }
                    }
                } else {
                    recent.forEach { game ->
                        PQCard(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    DifficultyBadge(game.level, small = true)
                                    Spacer(Modifier.height(4.dp))
                                    Text(game.date, color = TextSecondary, fontSize = 12.sp)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    val scoreColor = when {
                                        game.score >= 80 -> CorrectGreen
                                        game.score >= 50 -> LevelIntermediate
                                        else             -> WrongRed
                                    }
                                    Text(
                                        "${game.score} pts",
                                        color      = scoreColor,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize   = 17.sp
                                    )
                                    Text("${game.correct}/${game.total}", color = TextSecondary, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

// ── Private helpers ───────────────────────────────────────────────────────────

@Composable
private fun HomeStatChip(emoji: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(emoji, fontSize = 18.sp)
        Text(value, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
        Text(label, color = Color.White.copy(alpha = 0.65f), fontSize = 10.sp)
    }
}

@Composable
private fun LevelRowCard(
    level: DifficultyLevel,
    description: String,
    icon: ImageVector,
    questions: Int,
    onClick: () -> Unit
) {
    val color = when (level) {
        DifficultyLevel.BASICO      -> LevelBasic
        DifficultyLevel.INTERMEDIO  -> LevelIntermediate
        DifficultyLevel.AVANZADO    -> LevelAdvanced
    }
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape    = RoundedCornerShape(14.dp),
        colors   = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = color, modifier = Modifier.size(26.dp))
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(level.label, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(description, color = TextSecondary, fontSize = 12.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("$questions", color = color, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                Text("preguntas", color = TextSecondary, fontSize = 10.sp)
            }
        }
    }
}

@Composable
private fun QuickAccessCard(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape    = RoundedCornerShape(14.dp),
        colors   = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = color, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.height(8.dp))
            Text(label, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}
