package com.programacion.prograquiz.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary          = PrimaryBlue,
    onPrimary        = TextPrimary,
    primaryContainer = PrimaryBlueDark,
    secondary        = SecondaryPurple,
    onSecondary      = TextPrimary,
    background       = BackgroundDark,
    onBackground     = TextPrimary,
    surface          = SurfaceDark,
    onSurface        = TextPrimary,
    surfaceVariant   = CardDark,
    onSurfaceVariant = TextSecondary,
    tertiary         = AccentCyan,
    error            = WrongRed,
    outline          = DividerColor
)

@Composable
fun PrograQuizTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography  = Typography,
        content     = content
    )
}
