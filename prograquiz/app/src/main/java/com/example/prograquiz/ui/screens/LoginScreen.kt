package com.example.prograquiz.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prograquiz.ui.components.PQPrimaryButton
import com.example.prograquiz.ui.components.PrograQuizLogo
import com.example.prograquiz.ui.theme.*
import com.example.prograquiz.viewmodel.SessionViewModel

@Composable
fun LoginScreen(
    sessionViewModel: SessionViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email   by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPwd  by remember { mutableStateOf(false) }

    val formState by sessionViewModel.loginForm.collectAsState()
    val focusManager = LocalFocusManager.current

    // Clear errors when user types
    LaunchedEffect(email, password) { sessionViewModel.clearLoginError() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(Color(0xFF0A1628), BackgroundDark, SurfaceDark))
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(64.dp))

            // ── Brand ──────────────────────────────────────────────────────────
            PrograQuizLogo(size = 88)
            Spacer(Modifier.height(20.dp))
            Text(
                text       = "PrograQuiz",
                fontSize   = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color      = TextPrimary,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text  = "Aprende programación jugando",
                color = TextSecondary,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(44.dp))

            // ── General error banner ───────────────────────────────────────────
            AnimatedVisibility(visible = formState.generalError != null) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    shape    = RoundedCornerShape(12.dp),
                    colors   = CardDefaults.cardColors(containerColor = WrongRed.copy(alpha = 0.12f))
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ErrorOutline, null, tint = WrongRed, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(formState.generalError ?: "", color = WrongRed, fontSize = 13.sp)
                    }
                }
            }

            // ── Email ──────────────────────────────────────────────────────────
            OutlinedTextField(
                value         = email,
                onValueChange = { email = it },
                label         = { Text("Correo electrónico", color = TextSecondary) },
                leadingIcon   = { Icon(Icons.Default.Email, null, tint = PrimaryBlue) },
                isError       = formState.emailError != null,
                supportingText = formState.emailError?.let { { Text(it, color = WrongRed) } },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction    = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                colors     = pqTextFieldColors(),
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            // ── Password ───────────────────────────────────────────────────────
            OutlinedTextField(
                value         = password,
                onValueChange = { password = it },
                label         = { Text("Contraseña", color = TextSecondary) },
                leadingIcon   = { Icon(Icons.Default.Lock, null, tint = PrimaryBlue) },
                trailingIcon  = {
                    IconButton(onClick = { showPwd = !showPwd }) {
                        Icon(
                            if (showPwd) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            null, tint = TextSecondary
                        )
                    }
                },
                visualTransformation = if (showPwd) VisualTransformation.None else PasswordVisualTransformation(),
                isError       = formState.passwordError != null,
                supportingText = formState.passwordError?.let { { Text(it, color = WrongRed) } },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction    = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        sessionViewModel.login(email, password, onLoginSuccess)
                    }
                ),
                colors     = pqTextFieldColors(),
                singleLine = true
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text     = "¿Olvidaste tu contraseña?",
                color    = PrimaryBlue,
                fontSize = 13.sp,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(Modifier.height(28.dp))

            // ── Login button ───────────────────────────────────────────────────
            PQPrimaryButton(
                text     = if (formState.isLoading) "Ingresando..." else "Iniciar Sesión",
                onClick  = {
                    focusManager.clearFocus()
                    sessionViewModel.login(email, password, onLoginSuccess)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled  = !formState.isLoading,
                icon     = Icons.Default.Login
            )

            Spacer(Modifier.height(20.dp))

            // ── Divider ────────────────────────────────────────────────────────
            Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = DividerColor)
                Text("  o  ", color = TextHint, fontSize = 12.sp)
                HorizontalDivider(modifier = Modifier.weight(1f), color = DividerColor)
            }

            Spacer(Modifier.height(16.dp))

            // ── Demo access ────────────────────────────────────────────────────
            OutlinedButton(
                onClick  = { sessionViewModel.demoLogin(onLoginSuccess) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape    = RoundedCornerShape(14.dp),
                border   = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
                colors   = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary)
            ) {
                Icon(Icons.Default.Code, null, tint = AccentCyan, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Acceso Demo", fontWeight = FontWeight.Medium)
            }

            Spacer(Modifier.height(36.dp))

            // ── Register link ──────────────────────────────────────────────────
            Row {
                Text("¿No tienes cuenta? ", color = TextSecondary, fontSize = 14.sp)
                Text(
                    text       = "Regístrate",
                    color      = PrimaryBlue,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier   = Modifier.clickable { onNavigateToRegister() }
                )
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
fun pqTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor      = PrimaryBlue,
    unfocusedBorderColor    = DividerColor,
    focusedTextColor        = TextPrimary,
    unfocusedTextColor      = TextPrimary,
    cursorColor             = PrimaryBlue,
    focusedContainerColor   = CardDark,
    unfocusedContainerColor = CardDarker,
    errorBorderColor        = WrongRed,
    errorTextColor          = TextPrimary,
    errorContainerColor     = WrongRed.copy(alpha = 0.06f)
)
