package com.programacion.prograquiz.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programacion.prograquiz.model.DifficultyLevel
import com.programacion.prograquiz.ui.components.AvatarCircle
import com.programacion.prograquiz.ui.components.DifficultyBadge
import com.programacion.prograquiz.ui.components.PQCard
import com.programacion.prograquiz.ui.components.SectionHeader
import com.programacion.prograquiz.ui.theme.*
import com.programacion.prograquiz.viewmodel.SessionViewModel

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

    Scaffold(
        containerColor = BackgroundDark,
        bottomBar = {
            NavigationBar(containerColor = SurfaceDark) {
                listOf(
                    Triple(Icons.Default.Home,        "Inicio",    null as (() -> Unit)?),
                    Triple(Icons.Default.Leaderboard, "Ranking",   onNavigateToRanking),
                    Triple(Icons.Default.History,     "Historial", onNavigateToHistory),
                    Triple(Icons.Default.Person,      "Perfil",    onNavigateToProfile)
                ).forEachIndexed { idx, (icon, label, action) ->
                    NavigationBarItem(
                        selected = idx == 0,
                        onClick  = { action?.invoke() },
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
            // Cabecera
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceDark)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AvatarCircle(
                        initials        = user?.avatarInitials ?: "?",
                        size            = 44,
                        backgroundColor = PrimaryBlue
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Hola, ${user?.username ?: ""}",
                            color      = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 16.sp
                        )
                        Text(
                            user?.email ?: "",
                            color    = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                // Botón jugar
                Button(
                    onClick  = onNavigateToLevelSelect,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Icon(Icons.Default.PlayArrow, null, modifier = Modifier.size(22.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Jugar", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.White)
                }

                Spacer(Modifier.height(24.dp))

                // Niveles
                SectionHeader("Niveles disponibles")
                Spacer(Modifier.height(10.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    LevelRow(DifficultyLevel.BASICO,     Icons.Default.LooksOne, onNavigateToLevelSelect)
                    LevelRow(DifficultyLevel.INTERMEDIO, Icons.Default.LooksTwo, onNavigateToLevelSelect)
                    LevelRow(DifficultyLevel.AVANZADO,   Icons.Default.Looks3,   onNavigateToLevelSelect)
                }

                Spacer(Modifier.height(24.dp))

                // Últimas partidas
                SectionHeader("Últimas partidas", "Ver todo", onNavigateToHistory)
                Spacer(Modifier.height(10.dp))

                val recent = history.take(3)
                if (recent.isEmpty()) {
                    Text(
                        "Aún no has jugado ninguna partida.",
                        color    = TextSecondary,
                        fontSize = 13.sp
                    )
                } else {
                    recent.forEach { game ->
                        val scoreColor = when {
                            game.score >= 80 -> CorrectGreen
                            game.score >= 50 -> LevelIntermediate
                            else             -> WrongRed
                        }
                        PQCard(modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment     = Alignment.CenterVertically
                            ) {
                                Column {
                                    DifficultyBadge(game.level, small = true)
                                    Spacer(Modifier.height(3.dp))
                                    Text(game.date, color = TextSecondary, fontSize = 11.sp)
                                }
                                Text(
                                    "${game.score} pts",
                                    color      = scoreColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize   = 15.sp
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun LevelRow(level: DifficultyLevel, icon: ImageVector, onClick: () -> Unit) {
    val color = when (level) {
        DifficultyLevel.BASICO     -> LevelBasic
        DifficultyLevel.INTERMEDIO -> LevelIntermediate
        DifficultyLevel.AVANZADO   -> LevelAdvanced
    }
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape    = RoundedCornerShape(10.dp),
        colors   = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Row(
            modifier          = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = color, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.width(12.dp))
            Text(
                level.label, color = TextPrimary,
                fontWeight = FontWeight.Medium, fontSize = 15.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.Default.ChevronRight, null, tint = TextHint, modifier = Modifier.size(18.dp))
        }
    }
}
