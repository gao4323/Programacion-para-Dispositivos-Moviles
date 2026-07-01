package com.example.prograquiz.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prograquiz.ui.components.*
import com.example.prograquiz.ui.theme.*
import com.example.prograquiz.viewmodel.SessionViewModel

@Composable
fun RegisterScreen(
    sessionViewModel: SessionViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var username   by remember { mutableStateOf("") }
    var email      by remember { mutableStateOf("") }
    var password   by remember { mutableStateOf("") }
    var confirmPwd by remember { mutableStateOf("") }
    var showPwd    by remember { mutableStateOf(false) }
    var acceptTerms by remember { mutableStateOf(false) }

    val formState    by sessionViewModel.registerForm.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = { PQTopBar(title = "Crear cuenta", onBack = onNavigateBack) },
        containerColor = BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Brush.verticalGradient(listOf(BackgroundDark, SurfaceDark)))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))

            Text(
                "Únete a PrograQuiz",
                style      = MaterialTheme.typography.headlineMedium,
                color      = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Text("Crea tu cuenta y empieza a practicar", color = TextSecondary, fontSize = 14.sp)

            Spacer(Modifier.height(28.dp))

            // Username
            OutlinedTextField(
                value         = username,
                onValueChange = { username = it },
                label         = { Text("Nombre de usuario", color = TextSecondary) },
                leadingIcon   = { Icon(Icons.Default.Person, null, tint = PrimaryBlue) },
                isError       = formState.usernameError != null,
                supportingText = formState.usernameError?.let { { Text(it, color = WrongRed) } },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                colors     = pqTextFieldColors(),
                singleLine = true
            )
            Spacer(Modifier.height(10.dp))

            // Email
            OutlinedTextField(
                value         = email,
                onValueChange = { email = it },
                label         = { Text("Correo electrónico", color = TextSecondary) },
                leadingIcon   = { Icon(Icons.Default.Email, null, tint = PrimaryBlue) },
                isError       = formState.emailError != null,
                supportingText = formState.emailError?.let { { Text(it, color = WrongRed) } },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                colors     = pqTextFieldColors(),
                singleLine = true
            )
            Spacer(Modifier.height(10.dp))

            // Password
            OutlinedTextField(
                value         = password,
                onValueChange = { password = it },
                label         = { Text("Contraseña", color = TextSecondary) },
                leadingIcon   = { Icon(Icons.Default.Lock, null, tint = PrimaryBlue) },
                trailingIcon  = {
                    IconButton(onClick = { showPwd = !showPwd }) {
                        Icon(if (showPwd) Icons.Default.VisibilityOff else Icons.Default.Visibility, null, tint = TextSecondary)
                    }
                },
                visualTransformation = if (showPwd) VisualTransformation.None else PasswordVisualTransformation(),
                isError       = formState.passwordError != null,
                supportingText = formState.passwordError?.let { { Text(it, color = WrongRed) } },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                colors     = pqTextFieldColors(),
                singleLine = true
            )
            Spacer(Modifier.height(10.dp))

            // Confirm password
            OutlinedTextField(
                value         = confirmPwd,
                onValueChange = { confirmPwd = it },
                label         = { Text("Confirmar contraseña", color = TextSecondary) },
                leadingIcon   = { Icon(Icons.Default.LockOpen, null, tint = PrimaryBlue) },
                visualTransformation = PasswordVisualTransformation(),
                isError       = formState.confirmError != null,
                supportingText = formState.confirmError?.let { { Text(it, color = WrongRed) } },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                colors     = pqTextFieldColors(),
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            // Password strength indicator
            if (password.isNotEmpty()) {
                PasswordStrengthBar(password)
                Spacer(Modifier.height(8.dp))
            }

            // Terms
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = acceptTerms,
                    onCheckedChange = { acceptTerms = it },
                    colors = CheckboxDefaults.colors(checkedColor = PrimaryBlue)
                )
                Text(
                    "Acepto los términos y condiciones",
                    color    = TextSecondary,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(24.dp))

            PQPrimaryButton(
                text    = if (formState.isLoading) "Creando cuenta..." else "Crear cuenta",
                onClick = {
                    focusManager.clearFocus()
                    sessionViewModel.register(username, email, password, confirmPwd, onRegisterSuccess)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled  = acceptTerms && !formState.isLoading,
                icon     = Icons.Default.PersonAdd
            )

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun PasswordStrengthBar(password: String) {
    val strength = when {
        password.length >= 12 && password.any { it.isUpperCase() } && password.any { it.isDigit() } -> 3
        password.length >= 8  && (password.any { it.isUpperCase() } || password.any { it.isDigit() }) -> 2
        password.length >= 6  -> 1
        else -> 0
    }
    val label = listOf("Muy débil", "Débil", "Media", "Fuerte").getOrElse(strength) { "" }
    val color = listOf(WrongRed, LevelIntermediate, AccentCyan, CorrectGreen).getOrElse(strength) { WrongRed }

    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            repeat(4) { idx ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(
                            if (idx <= strength - 1) color else DividerColor,
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Text("Fortaleza: $label", color = color, fontSize = 11.sp)
    }
}
