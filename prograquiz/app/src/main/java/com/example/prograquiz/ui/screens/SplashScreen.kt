package com.example.prograquiz.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prograquiz.ui.components.PrograQuizLogo
import com.example.prograquiz.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    val alpha  by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(800), label = "alpha"
    )
    val scale  by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = "scale"
    )

    LaunchedEffect(Unit) {
        delay(2000L)
        onNavigateToLogin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(BackgroundDark, SurfaceDark, CardDark)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alpha).scale(scale)
        ) {
            PrograQuizLogo(size = 100)
            Spacer(Modifier.height(24.dp))
            Text(
                text       = "PrograQuiz",
                fontSize   = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color      = TextPrimary,
                fontFamily = FontFamily.Monospace
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text     = "Aprende programación jugando",
                fontSize = 15.sp,
                color    = TextSecondary
            )
            Spacer(Modifier.height(48.dp))
            // Dots loader
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) { idx ->
                    val dotAlpha by animateFloatAsState(
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(400, delayMillis = idx * 150),
                            repeatMode = RepeatMode.Reverse
                        ), label = "dot$idx"
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .alpha(dotAlpha)
                            .background(PrimaryBlue, shape = androidx.compose.foundation.shape.CircleShape)
                    )
                }
            }
        }

        // Version tag
        Text(
            text     = "v1.0 · Universidad La Salle",
            color    = TextHint,
            fontSize = 11.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}
