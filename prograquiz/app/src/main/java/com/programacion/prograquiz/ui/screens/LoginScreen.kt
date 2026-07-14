package com.programacion.prograquiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programacion.prograquiz.ui.components.PrograQuizLogo
import com.programacion.prograquiz.ui.theme.*
import com.programacion.prograquiz.viewmodel.SessionViewModel

@Composable
fun LoginScreen(
    sessionViewModel: SessionViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPwd  by remember { mutableStateOf(false) }

    val error     by sessionViewModel.loginError.collectAsState()
    val isLoading by sessionViewModel.isLoading.collectAsState()
    val focus     = LocalFocusManager.current

    LaunchedEffect(email, password) { sessionViewModel.clearLoginError() }

    val tfColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor      = PrimaryBlue,
        unfocusedBorderColor    = DividerColor,
        focusedTextColor        = TextPrimary,
        unfocusedTextColor      = TextPrimary,
        cursorColor             = PrimaryBlue,
        focusedContainerColor   = CardDark,
        unfocusedContainerColor = CardDarker
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PrograQuizLogo(size = 72)
        Spacer(Modifier.height(16.dp))
        Text(
            "PrograQuiz",
            fontSize   = 26.sp,
            fontWeight = FontWeight.Bold,
            color      = TextPrimary,
            fontFamily = FontFamily.Monospace
        )
        Text(
            "Practica lógica de programación",
            fontSize  = 13.sp,
            color     = TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(40.dp))

        OutlinedTextField(
            value           = email,
            onValueChange   = { email = it },
            label           = { Text("Correo", color = TextSecondary) },
            leadingIcon     = { Icon(Icons.Default.Email, null, tint = PrimaryBlue) },
            modifier        = Modifier.fillMaxWidth(),
            shape           = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focus.moveFocus(androidx.compose.ui.focus.FocusDirection.Down) }),
            colors          = tfColors,
            singleLine      = true
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value                = password,
            onValueChange        = { password = it },
            label                = { Text("Contraseña", color = TextSecondary) },
            leadingIcon          = { Icon(Icons.Default.Lock, null, tint = PrimaryBlue) },
            trailingIcon         = {
                IconButton(onClick = { showPwd = !showPwd }) {
                    Icon(
                        if (showPwd) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        null, tint = TextSecondary
                    )
                }
            },
            visualTransformation = if (showPwd) VisualTransformation.None else PasswordVisualTransformation(),
            modifier             = Modifier.fillMaxWidth(),
            shape                = RoundedCornerShape(12.dp),
            keyboardOptions      = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions      = KeyboardActions(onDone = {
                focus.clearFocus()
                sessionViewModel.login(email, password, onLoginSuccess)
            }),
            colors     = tfColors,
            singleLine = true
        )

        if (error != null) {
            Spacer(Modifier.height(8.dp))
            Text(error!!, color = WrongRed, fontSize = 13.sp, textAlign = TextAlign.Center)
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick  = {
                focus.clearFocus()
                sessionViewModel.login(email, password, onLoginSuccess)
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape    = RoundedCornerShape(12.dp),
            enabled  = !isLoading,
            colors   = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            } else {
                Text("Ingresar", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.White)
            }
        }

        Spacer(Modifier.height(16.dp))

        Row {
            Text("¿No tienes cuenta? ", color = TextSecondary, fontSize = 14.sp)
            Text(
                "Regístrate",
                color      = PrimaryBlue,
                fontSize   = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier   = Modifier.clickable { onNavigateToRegister() }
            )
        }
    }
}
