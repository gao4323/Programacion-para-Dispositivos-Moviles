package com.programacion.prograquiz.ui.screens

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programacion.prograquiz.ui.components.PQTopBar
import com.programacion.prograquiz.ui.theme.*
import com.programacion.prograquiz.viewmodel.SessionViewModel

@Composable
fun RegisterScreen(
    sessionViewModel: SessionViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var username    by remember { mutableStateOf("") }
    var email       by remember { mutableStateOf("") }
    var password    by remember { mutableStateOf("") }
    var confirmPwd  by remember { mutableStateOf("") }
    var showPwd     by remember { mutableStateOf(false) }

    val error     by sessionViewModel.registerError.collectAsState()
    val isLoading by sessionViewModel.isLoading.collectAsState()
    val focus     = LocalFocusManager.current

    LaunchedEffect(username, email, password, confirmPwd) {
        sessionViewModel.clearRegisterError()
    }

    val tfColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor      = PrimaryBlue,
        unfocusedBorderColor    = DividerColor,
        focusedTextColor        = TextPrimary,
        unfocusedTextColor      = TextPrimary,
        cursorColor             = PrimaryBlue,
        focusedContainerColor   = CardDark,
        unfocusedContainerColor = CardDarker
    )

    Scaffold(
        topBar         = { PQTopBar(title = "Crear cuenta", onBack = onNavigateBack) },
        containerColor = BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundDark)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))

            Text(
                "Regístrate",
                fontSize   = 22.sp,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary
            )
            Text(
                "Crea tu cuenta para guardar tu progreso",
                fontSize  = 13.sp,
                color     = TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(28.dp))

            // Nombre de usuario
            OutlinedTextField(
                value           = username,
                onValueChange   = { username = it },
                label           = { Text("Nombre de usuario", color = TextSecondary) },
                leadingIcon     = { Icon(Icons.Default.Person, null, tint = PrimaryBlue) },
                modifier        = Modifier.fillMaxWidth(),
                shape           = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Down) }),
                colors          = tfColors,
                singleLine      = true
            )
            Spacer(Modifier.height(10.dp))

            // Correo
            OutlinedTextField(
                value           = email,
                onValueChange   = { email = it },
                label           = { Text("Correo electrónico", color = TextSecondary) },
                leadingIcon     = { Icon(Icons.Default.Email, null, tint = PrimaryBlue) },
                modifier        = Modifier.fillMaxWidth(),
                shape           = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Down) }),
                colors          = tfColors,
                singleLine      = true
            )
            Spacer(Modifier.height(10.dp))

            // Contraseña
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
                keyboardOptions      = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                keyboardActions      = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Down) }),
                colors               = tfColors,
                singleLine           = true
            )
            Spacer(Modifier.height(10.dp))

            // Confirmar contraseña
            OutlinedTextField(
                value                = confirmPwd,
                onValueChange        = { confirmPwd = it },
                label                = { Text("Confirmar contraseña", color = TextSecondary) },
                leadingIcon          = { Icon(Icons.Default.Lock, null, tint = PrimaryBlue) },
                visualTransformation = PasswordVisualTransformation(),
                modifier             = Modifier.fillMaxWidth(),
                shape                = RoundedCornerShape(12.dp),
                keyboardOptions      = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions      = KeyboardActions(onDone = { focus.clearFocus() }),
                colors               = tfColors,
                singleLine           = true
            )

            // Error
            if (error != null) {
                Spacer(Modifier.height(10.dp))
                Text(
                    text      = error!!,
                    color     = WrongRed,
                    fontSize  = 13.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick  = {
                    focus.clearFocus()
                    sessionViewModel.register(username, email, password, confirmPwd, onRegisterSuccess)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape    = RoundedCornerShape(12.dp),
                enabled  = !isLoading,
                colors   = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = androidx.compose.ui.graphics.Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text("Crear cuenta", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }
            }

            Spacer(Modifier.height(16.dp))

            Row {
                Text("¿Ya tienes cuenta? ", color = TextSecondary, fontSize = 14.sp)
                Text(
                    "Inicia sesión",
                    color      = PrimaryBlue,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier   = Modifier.clickable { onNavigateBack() }
                )
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}
