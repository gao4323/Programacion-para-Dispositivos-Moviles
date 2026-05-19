package com.example.parcial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WelcomeScreen(navController: NavController) {

    // Columna principal
    Column(
        modifier = Modifier
            .fillMaxSize()

            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFEC4899), Color(0xFF86198F))
                )
            )
            .padding(30.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        // Título
        Text(
            text = "COLOR RUSH",
            fontSize = 50.sp,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Descripción
        Text(
            text = "Presiona el botón del color correcto antes que termine el tiempo.",
            fontSize = 20.sp,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(50.dp))

        //Boton
        Button(
            onClick = {
                navController.navigate("game")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFFFFF)
            )
        ) {
            Text(
                text = "Comenzar",
                fontSize = 24.sp,
                color = Color.Black
            )
        }
    }
}