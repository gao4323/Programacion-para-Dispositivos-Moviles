package com.example.prograquiz.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prograquiz.model.DifficultyLevel
import com.example.prograquiz.ui.theme.*

// ── TopBar ────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PQTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text  = title,
                style = MaterialTheme.typography.headlineSmall,
                color = TextPrimary
            )
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = TextPrimary
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SurfaceDark
        )
    )
}

// ── App Logo / Brand ──────────────────────────────────────────────────────────

@Composable
fun PrograQuizLogo(size: Int = 64) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(RoundedCornerShape((size * 0.25).dp))
                .background(
                    Brush.linearGradient(listOf(PrimaryBlue, SecondaryPurple))
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text      = "</>",
                color     = Color.White,
                fontSize  = (size * 0.35).sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

// ── Avatar circle ─────────────────────────────────────────────────────────────

@Composable
fun AvatarCircle(
    initials: String,
    size: Int = 48,
    backgroundColor: Color = PrimaryBlue
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text      = initials,
            color     = Color.White,
            fontSize  = (size * 0.35).sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ── Difficulty badge ──────────────────────────────────────────────────────────

@Composable
fun DifficultyBadge(level: DifficultyLevel, small: Boolean = false) {
    val color = when (level) {
        DifficultyLevel.BASICO      -> LevelBasic
        DifficultyLevel.INTERMEDIO  -> LevelIntermediate
        DifficultyLevel.AVANZADO    -> LevelAdvanced
    }
    Surface(
        shape = RoundedCornerShape(50),
        color = color.copy(alpha = 0.15f)
    ) {
        Text(
            text     = level.label,
            color    = color,
            fontSize = if (small) 10.sp else 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                horizontal = if (small) 8.dp else 12.dp,
                vertical   = if (small) 3.dp else 5.dp
            )
        )
    }
}

// ── PQ Primary Button ─────────────────────────────────────────────────────────

@Composable
fun PQPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    Button(
        onClick  = onClick,
        enabled  = enabled,
        modifier = modifier.height(52.dp),
        shape    = RoundedCornerShape(16.dp),
        colors   = ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue,
            contentColor   = Color.White,
            disabledContainerColor = CardDark,
            disabledContentColor   = TextHint
        )
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text = text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

// ── PQ Outline Button ─────────────────────────────────────────────────────────

@Composable
fun PQOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    OutlinedButton(
        onClick  = onClick,
        modifier = modifier.height(52.dp),
        shape    = RoundedCornerShape(16.dp),
        colors   = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue),
        border   = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue)
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text = text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

// ── PQ Card ───────────────────────────────────────────────────────────────────

@Composable
fun PQCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = CardDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), content = content)
    }
}

// ── Quiz Answer Option ────────────────────────────────────────────────────────

@Composable
fun AnswerOption(
    text: String,
    index: Int,
    isSelected: Boolean,
    isAnswered: Boolean,
    isCorrect: Boolean,
    onClick: () -> Unit
) {
    val optionLabels = listOf("A", "B", "C", "D")
    val label = optionLabels.getOrNull(index) ?: "${index + 1}"

    val borderColor by animateColorAsState(
        targetValue = when {
            isAnswered && isCorrect  -> CorrectGreen
            isAnswered && isSelected -> WrongRed
            isSelected               -> PrimaryBlue
            else                     -> DividerColor
        },
        animationSpec = tween(300), label = "border"
    )
    val bgColor by animateColorAsState(
        targetValue = when {
            isAnswered && isCorrect  -> CorrectGreen.copy(alpha = 0.15f)
            isAnswered && isSelected -> WrongRed.copy(alpha = 0.15f)
            isSelected               -> PrimaryBlue.copy(alpha = 0.15f)
            else                     -> CardDarker
        },
        animationSpec = tween(300), label = "bg"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(14.dp))
            .clickable(enabled = !isAnswered) { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(borderColor.copy(alpha = 0.2f))
                .border(1.dp, borderColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = label, color = borderColor, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
        Spacer(Modifier.width(14.dp))
        Text(
            text  = text,
            color = TextPrimary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}

// ── Stat card (for profile) ───────────────────────────────────────────────────

@Composable
fun StatCard(
    value: String,
    label: String,
    icon: ImageVector,
    iconColor: Color = PrimaryBlue,
    modifier: Modifier = Modifier
) {
    PQCard(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text       = value,
                style      = MaterialTheme.typography.headlineMedium,
                color      = TextPrimary,
                fontWeight = FontWeight.Bold,
                textAlign  = TextAlign.Center
            )
            Text(
                text  = label,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ── Section header ────────────────────────────────────────────────────────────

@Composable
fun SectionHeader(title: String, action: String? = null, onAction: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text       = title,
            style      = MaterialTheme.typography.titleLarge,
            color      = TextPrimary,
            fontWeight = FontWeight.Bold
        )
        if (action != null && onAction != null) {
            TextButton(onClick = onAction) {
                Text(text = action, color = PrimaryBlue, fontSize = 13.sp)
            }
        }
    }
}

// ── Progress indicator ────────────────────────────────────────────────────────

@Composable
fun QuizProgressBar(progress: Float, current: Int, total: Int) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Pregunta $current de $total", color = TextSecondary, fontSize = 13.sp)
            Text("${(progress * 100).toInt()}%", color = PrimaryBlue, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            progress   = { progress },
            modifier   = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
            color      = PrimaryBlue,
            trackColor = CardDark
        )
    }
}

// ── Score ring ────────────────────────────────────────────────────────────────

@Composable
fun ScoreRing(score: Int, size: Int = 120) {
    val color = when {
        score >= 80 -> CorrectGreen
        score >= 50 -> LevelIntermediate
        else        -> WrongRed
    }
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.12f))
            .border(4.dp, color, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text       = "$score",
                color      = color,
                fontSize   = (size * 0.33).sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(text = "pts", color = color.copy(alpha = 0.7f), fontSize = 13.sp)
        }
    }
}
