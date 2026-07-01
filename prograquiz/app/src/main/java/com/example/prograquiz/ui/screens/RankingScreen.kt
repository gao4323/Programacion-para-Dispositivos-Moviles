package com.example.prograquiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prograquiz.data.mock.MockData
import com.example.prograquiz.model.RankingEntry
import com.example.prograquiz.ui.components.*
import com.example.prograquiz.ui.theme.*

@Composable
fun RankingScreen(onNavigateBack: () -> Unit) {
    val ranking = MockData.rankingList
    val top3    = ranking.take(3)
    val rest    = ranking.drop(3)
    val myUser  = MockData.currentUser

    Scaffold(
        topBar = { PQTopBar(title = "Ranking Global", onBack = onNavigateBack) },
        containerColor = BackgroundDark
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Podium
            item {
                Spacer(Modifier.height(16.dp))
                PodiumSection(top3)
                Spacer(Modifier.height(20.dp))

                // My position card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape    = RoundedCornerShape(14.dp),
                    colors   = CardDefaults.cardColors(containerColor = PrimaryBlue.copy(alpha = 0.15f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Person, null, tint = PrimaryBlue, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Tu posición: #5 con 95 pts", color = PrimaryBlue, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(Modifier.height(16.dp))
                SectionHeader("Clasificación completa")
                Spacer(Modifier.height(10.dp))
            }

            // Positions 4+
            items(rest) { entry ->
                RankingRow(entry = entry, isCurrentUser = entry.username == myUser.username)
                Spacer(Modifier.height(8.dp))
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun PodiumSection(top3: List<RankingEntry>) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🏆 Top 3", color = GoldColor, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
        Spacer(Modifier.height(16.dp))

        // 2 - 1 - 3 layout
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            if (top3.size > 1) PodiumCard(top3[1], height = 110, medalColor = SilverColor)
            Spacer(Modifier.width(8.dp))
            if (top3.isNotEmpty()) PodiumCard(top3[0], height = 140, medalColor = GoldColor)
            Spacer(Modifier.width(8.dp))
            if (top3.size > 2) PodiumCard(top3[2], height = 90, medalColor = BronzeColor)
        }
    }
}

@Composable
private fun PodiumCard(entry: RankingEntry, height: Int, medalColor: Color) {
    Column(
        modifier = Modifier.width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AvatarCircle(entry.avatarInitials, size = 44, backgroundColor = medalColor)
        Spacer(Modifier.height(4.dp))
        Text(entry.username.take(8), color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Text("${entry.score}pts", color = medalColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .width(90.dp)
                .height(height.dp)
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(Brush.verticalGradient(listOf(medalColor.copy(0.3f), medalColor.copy(0.1f)))),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "#${entry.position}",
                color      = medalColor,
                fontWeight = FontWeight.ExtraBold,
                fontSize   = 22.sp
            )
        }
    }
}

@Composable
private fun RankingRow(entry: RankingEntry, isCurrentUser: Boolean) {
    val bgColor = if (isCurrentUser) PrimaryBlue.copy(alpha = 0.12f) else CardDark
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "#${entry.position}",
                color      = TextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize   = 14.sp,
                modifier   = Modifier.width(32.dp)
            )
            AvatarCircle(entry.avatarInitials, size = 36)
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
                fontWeight = FontWeight.ExtraBold,
                fontSize   = 15.sp
            )
        }
    }
}
