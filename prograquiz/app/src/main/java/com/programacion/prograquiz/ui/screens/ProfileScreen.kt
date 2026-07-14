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
import com.programacion.prograquiz.ui.components.AvatarCircle
import com.programacion.prograquiz.ui.components.PQTopBar
import com.programacion.prograquiz.ui.theme.*
import com.programacion.prograquiz.viewmodel.SessionViewModel

@Composable
fun ProfileScreen(
    sessionViewModel: SessionViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onLogout: () -> Unit
) {
    val user by sessionViewModel.currentUser.collectAsState()
    val accuracy = if ((user?.totalQuestions ?: 0) > 0)
        (user!!.totalCorrect * 100 / user!!.totalQuestions) else 0

    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title   = { Text("Cerrar sesión", color = TextPrimary) },
            text    = { Text("¿Estás seguro de que deseas cerrar sesión?", color = TextSecondary) },
            confirmButton = {
                Button(
                    onClick = { showLogoutDialog = false; onLogout() },
                    colors  = ButtonDefaults.buttonColors(containerColor = WrongRed)
                ) { Text("Cerrar sesión", color = Color.White) }
            },
            dismissButton = {
                OutlinedButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancelar", color = TextSecondary)
                }
            },
            containerColor = CardDark
        )
    }

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

            AvatarCircle(
                initials         = user?.avatarInitials ?: "?",
                size             = 72,
                backgroundColor  = PrimaryBlue
            )
            Spacer(Modifier.height(12.dp))
            Text(
                user?.username ?: "",
                color      = TextPrimary,
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(user?.email ?: "", color = TextSecondary, fontSize = 13.sp)

            Spacer(Modifier.height(28.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(12.dp),
                colors   = CardDefaults.cardColors(containerColor = CardDark)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatRow(Icons.Default.EmojiEvents, "Mejor puntaje",      "${user?.bestScore ?: 0} pts", GoldColor)
                    HorizontalDivider(color = DividerColor)
                    StatRow(Icons.Default.SportsEsports, "Partidas jugadas", "${user?.totalGames ?: 0}",    PrimaryBlue)
                    HorizontalDivider(color = DividerColor)
                    StatRow(Icons.Default.CheckCircle, "Respuestas correctas", "${user?.totalCorrect ?: 0}", CorrectGreen)
                    HorizontalDivider(color = DividerColor)
                    StatRow(Icons.Default.TrendingUp, "Precisión",            "$accuracy%",                 AccentCyan)
                }
            }

            Spacer(Modifier.height(16.dp))

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

            Spacer(Modifier.height(12.dp))

            Button(
                onClick  = { showLogoutDialog = true },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape    = RoundedCornerShape(10.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor = WrongRed.copy(alpha = 0.15f),
                    contentColor   = WrongRed
                )
            ) {
                Icon(Icons.Default.Logout, null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Cerrar sesión", fontWeight = FontWeight.SemiBold)
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
