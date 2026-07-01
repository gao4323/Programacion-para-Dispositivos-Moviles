package com.example.prograquiz.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prograquiz.model.DifficultyLevel
import com.example.prograquiz.model.GameHistory
import com.example.prograquiz.ui.components.*
import com.example.prograquiz.ui.theme.*
import com.example.prograquiz.viewmodel.SessionViewModel

@Composable
fun HistoryScreen(
    sessionViewModel: SessionViewModel,
    onNavigateBack: () -> Unit
) {
    val allHistory by sessionViewModel.history.collectAsState()

    var selectedFilter by remember { mutableStateOf<DifficultyLevel?>(null) }
    var sortDescending by remember { mutableStateOf(true) }

    val filteredHistory = remember(allHistory, selectedFilter, sortDescending) {
        val filtered = if (selectedFilter == null) allHistory
                       else allHistory.filter { it.level == selectedFilter }
        if (sortDescending) filtered else filtered.sortedBy { it.score }
    }

    val avgScore = if (filteredHistory.isNotEmpty())
        filteredHistory.map { it.score }.average().toInt() else 0
    val bestScore = filteredHistory.maxOfOrNull { it.score } ?: 0
    val totalGames = filteredHistory.size

    Scaffold(
        topBar = {
            PQTopBar(
                title = "Historial",
                onBack = onNavigateBack,
                actions = {
                    IconButton(onClick = { sortDescending = !sortDescending }) {
                        Icon(
                            if (sortDescending) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                            contentDescription = "Ordenar",
                            tint = PrimaryBlue
                        )
                    }
                }
            )
        },
        containerColor = BackgroundDark
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // ── Summary row ───────────────────────────────────────────────────
            item {
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        value    = "$totalGames",
                        label    = "Partidas",
                        icon     = Icons.Default.SportsEsports,
                        iconColor = PrimaryBlue,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        value    = "$avgScore",
                        label    = "Promedio",
                        icon     = Icons.Default.TrendingUp,
                        iconColor = AccentCyan,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        value    = "$bestScore",
                        label    = "Mejor",
                        icon     = Icons.Default.EmojiEvents,
                        iconColor = GoldColor,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(20.dp))
            }

            // ── Filter chips ──────────────────────────────────────────────────
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = selectedFilter == null,
                            onClick  = { selectedFilter = null },
                            label    = { Text("Todos") },
                            colors   = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryBlue.copy(alpha = 0.2f),
                                selectedLabelColor     = PrimaryBlue
                            )
                        )
                    }
                    items(DifficultyLevel.entries) { level ->
                        val levelColor = when (level) {
                            DifficultyLevel.BASICO      -> LevelBasic
                            DifficultyLevel.INTERMEDIO  -> LevelIntermediate
                            DifficultyLevel.AVANZADO    -> LevelAdvanced
                        }
                        FilterChip(
                            selected = selectedFilter == level,
                            onClick  = { selectedFilter = if (selectedFilter == level) null else level },
                            label    = { Text(level.label) },
                            colors   = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = levelColor.copy(alpha = 0.2f),
                                selectedLabelColor     = levelColor
                            )
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // ── List label ────────────────────────────────────────────────────
            item {
                SectionHeader(
                    title = if (selectedFilter != null) "Filtrado: ${selectedFilter?.label}" else "Todas las partidas"
                )
                Spacer(Modifier.height(10.dp))
            }

            // ── Empty state ───────────────────────────────────────────────────
            if (filteredHistory.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape    = RoundedCornerShape(16.dp),
                        colors   = CardDefaults.cardColors(containerColor = CardDark)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.SearchOff, null, tint = TextHint, modifier = Modifier.size(40.dp))
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    "No hay partidas con este filtro",
                                    color    = TextSecondary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            // ── History items ─────────────────────────────────────────────────
            items(filteredHistory, key = { it.id }) { game ->
                HistoryCard(game = game)
                Spacer(Modifier.height(10.dp))
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun HistoryCard(game: GameHistory) {
    val scoreColor = when {
        game.score >= 80 -> CorrectGreen
        game.score >= 50 -> LevelIntermediate
        else             -> WrongRed
    }

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(14.dp),
        colors   = CardDefaults.cardColors(containerColor = CardDark),
        onClick  = { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Circular progress mini
                Box(
                    modifier = Modifier.size(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress  = { game.correct.toFloat() / game.total },
                        modifier  = Modifier.size(50.dp),
                        color     = scoreColor,
                        trackColor = CardDarker,
                        strokeWidth = 4.dp
                    )
                    Text(
                        "${game.correct}/${game.total}",
                        color      = TextPrimary,
                        fontSize   = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    DifficultyBadge(game.level, small = true)
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CalendarToday, null,
                            tint     = TextHint,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(game.date, color = TextSecondary, fontSize = 12.sp)
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "${game.score}",
                        color      = scoreColor,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize   = 26.sp
                    )
                    Text("pts", color = TextSecondary, fontSize = 11.sp)
                }

                Spacer(Modifier.width(4.dp))
                Icon(
                    if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    null, tint = TextHint, modifier = Modifier.size(18.dp)
                )
            }

            // Expandable detail
            AnimatedVisibility(
                visible = expanded,
                enter   = expandVertically(),
                exit    = shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(color = DividerColor)
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        DetailStat("Correctas", "${game.correct}", CorrectGreen)
                        DetailStat("Incorrectas", "${game.total - game.correct}", WrongRed)
                        DetailStat("Precisión", "${game.correct * 100 / game.total}%", AccentCyan)
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = color, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(label, color = TextSecondary, fontSize = 11.sp)
    }
}
