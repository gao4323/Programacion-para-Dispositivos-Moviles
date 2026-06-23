package com.example.prograquiz.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(

    primary = PrimaryBlue,

    secondary = SecondaryPurple,

    tertiary = AccentCyan,

    background = DarkBackground,

    surface = DarkSurface,

    onPrimary = TextPrimary,

    onBackground = TextPrimary,

    onSurface = TextPrimary
)

@Composable
fun PrograQuizTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(

        colorScheme = DarkColors,

        typography = AppTypography,

        content = content
    )
}