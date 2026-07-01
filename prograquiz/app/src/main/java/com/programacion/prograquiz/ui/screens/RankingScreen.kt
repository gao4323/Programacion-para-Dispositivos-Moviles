package com.programacion.prograquiz.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programacion.prograquiz.data.mock.MockData
import com.programacion.prograquiz.model.RankingEntry
import com.programacion.prograquiz.ui.components.AvatarCircle
import com.programacion.prograquiz.ui.components.DifficultyBadge
import com.programacion.prograquiz.ui.components.PQTopBar
import com.programacion.prograquiz.ui.theme.*

@Composable
fun RankingScreen(onNavigateBack: () -> Unit) {
    val ranking = MockData.rankingList

    Scaffold(
        topBar         = { PQTopBar(title = "Ranking", onBack = onNavigateBack) },
        containerColor = BackgroundDark
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(ranking) { entry ->
                RankingCard(entry)
            }
        }
    }
}

@Composable
private fun RankingCard(entry: RankingEntry) {
    val isCurrentUser = entry.username == "Gabriel"
    val posColor = when (entry.position) {
        1    -> GoldColor
        2    -> SilverColor
        3    -> BronzeColor
        else -> TextSecondary
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(
            containerColor = if (isCurrentUser) PrimaryBlue.copy(alpha = 0.12f) else CardDark
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "#${entry.position}",
                color      = posColor,
                fontWeight = FontWeight.Bold,
                fontSize   = 14.sp,
                modifier   = Modifier.width(36.dp)
            )
            AvatarCircle(entry.avatarInitials, size = 36, backgroundColor = if (isCurrentUser) PrimaryBlue else SurfaceDark)
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    entry.username,
                    color      = if (isCurrentUser) PrimaryBlue else TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize   = 14.sp
                )
                DifficultyBadge(entry.level, small = true)
            }
            Text(
                "${entry.score} pts",
                color      = GoldColor,
                fontWeight = FontWeight.Bold,
                fontSize   = 14.sp
            )
        }
    }
}
