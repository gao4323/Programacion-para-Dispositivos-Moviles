package com.example.prograquiz.ui.screens
import com.example.prograquiz.data.mock.MockData

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
import com.example.prograquiz.ui.components.AvatarCircle
import com.example.prograquiz.ui.components.PQTopBar
import com.example.prograquiz.ui.theme.*
import com.example.prograquiz.viewmodel.SessionViewModel

@Composable
fun SettingsScreen(
    sessionViewModel: SessionViewModel,
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit
) {
    val user     by sessionViewModel.currentUser.collectAsState()
    val settings by sessionViewModel.settings.collectAsState()

    val displayUser = user ?: MockData.currentUser

    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            icon    = { Icon(Icons.Default.Logout, null, tint = WrongRed) },
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
        topBar = { PQTopBar(title = "Ajustes", onBack = onNavigateBack) },
        containerColor = BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            // ── User card ──────────────────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(16.dp),
                colors   = CardDefaults.cardColors(containerColor = CardDark)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AvatarCircle(displayUser.avatarInitials, size = 54, backgroundColor = PrimaryBlue)
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(displayUser.username, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(displayUser.email, color = TextSecondary, fontSize = 13.sp)
                    }
                    Icon(Icons.Default.Edit, null, tint = PrimaryBlue, modifier = Modifier.size(20.dp))
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Experience preferences ─────────────────────────────────────────
            SettingsGroupTitle("Experiencia")
            Spacer(Modifier.height(8.dp))

            ToggleRow(
                icon     = Icons.Default.VolumeUp,
                label    = "Efectos de sonido",
                subtitle = "Sonidos al responder preguntas",
                checked  = settings.soundEnabled,
                onToggle = { sessionViewModel.toggleSound() }
            )
            ToggleRow(
                icon     = Icons.Default.Vibration,
                label    = "Vibración",
                subtitle = "Feedback háptico en respuestas",
                checked  = settings.vibrationEnabled,
                onToggle = { sessionViewModel.toggleVibration() }
            )
            ToggleRow(
                icon     = Icons.Default.Notifications,
                label    = "Notificaciones",
                subtitle = "Recordatorios diarios de práctica",
                checked  = settings.notificationsEnabled,
                onToggle = { sessionViewModel.toggleNotifications() }
            )
            ToggleRow(
                icon     = Icons.Default.DarkMode,
                label    = "Modo oscuro",
                subtitle = "Tema oscuro en la aplicación",
                checked  = settings.darkMode,
                onToggle = { sessionViewModel.toggleDarkMode() }
            )

            Spacer(Modifier.height(20.dp))

            // ── Account ────────────────────────────────────────────────────────
            SettingsGroupTitle("Cuenta")
            Spacer(Modifier.height(8.dp))

            ActionRow(Icons.Default.Edit,          "Editar perfil",           TextPrimary)  {}
            ActionRow(Icons.Default.Lock,          "Cambiar contraseña",      TextPrimary)  {}
            ActionRow(Icons.Default.Shield,        "Privacidad",              TextPrimary)  {}
            ActionRow(Icons.Default.DeleteForever, "Eliminar cuenta",         WrongRed)     {}

            Spacer(Modifier.height(20.dp))

            // ── App info ───────────────────────────────────────────────────────
            SettingsGroupTitle("Información")
            Spacer(Modifier.height(8.dp))

            ActionRow(Icons.Default.Info,        "Acerca de PrograQuiz",   TextPrimary) {}
            ActionRow(Icons.Default.Description, "Términos y condiciones", TextPrimary) {}
            ActionRow(Icons.Default.Star,        "Calificar la app",       GoldColor)   {}

            Spacer(Modifier.height(24.dp))

            // ── Logout ─────────────────────────────────────────────────────────
            Button(
                onClick  = { showLogoutDialog = true },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape    = RoundedCornerShape(14.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor = WrongRed.copy(alpha = 0.12f),
                    contentColor   = WrongRed
                )
            ) {
                Icon(Icons.Default.Logout, null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Cerrar sesión", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "PrograQuiz v1.0 · Gabriel Jara · Universidad La Salle 2025",
                color    = TextHint,
                fontSize = 11.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(32.dp))
        }
    }
}

// ── Helpers ───────────────────────────────────────────────────────────────────

@Composable
private fun SettingsGroupTitle(title: String) {
    Text(
        text          = title.uppercase(),
        color         = TextHint,
        fontSize      = 11.sp,
        fontWeight    = FontWeight.Bold,
        letterSpacing = androidx.compose.ui.unit.TextUnit(1.5f, androidx.compose.ui.unit.TextUnitType.Sp)
    )
}

@Composable
private fun ToggleRow(
    icon: ImageVector,
    label: String,
    subtitle: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = PrimaryBlue, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(label, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text(subtitle, color = TextSecondary, fontSize = 11.sp)
            }
            Switch(
                checked = checked,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor   = Color.White,
                    checkedTrackColor   = PrimaryBlue,
                    uncheckedThumbColor = TextSecondary,
                    uncheckedTrackColor = CardDarker
                )
            )
        }
    }
}

@Composable
private fun ActionRow(
    icon: ImageVector,
    label: String,
    labelColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = CardDark),
        onClick  = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = if (labelColor == WrongRed) WrongRed else PrimaryBlue, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(14.dp))
            Text(label, color = labelColor, modifier = Modifier.weight(1f), fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Icon(Icons.Default.ChevronRight, null, tint = TextHint, modifier = Modifier.size(18.dp))
        }
    }
}
