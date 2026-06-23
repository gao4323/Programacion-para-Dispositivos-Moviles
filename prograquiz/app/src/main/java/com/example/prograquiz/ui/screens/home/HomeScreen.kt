package com.example.prograquiz.ui.screens.home


import androidx.compose.foundation.layout.*

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.prograquiz.data.mock.MockData
import com.example.prograquiz.ui.components.AppTopBar
import com.example.prograquiz.ui.components.DashboardCard

@Composable
fun HomeScreen(
    onPlay: () -> Unit,
    onRanking: () -> Unit,
    onHistory: () -> Unit,
    onProfile: () -> Unit,
    onSettings: () -> Unit
) {

    Scaffold(

        topBar = {
            AppTopBar("PrograQuiz")
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            Text(
                text = "Bienvenido ${MockData.currentUser.name}",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            DashboardCard(
                title = "Jugar Quiz",
                description = "Practica programación."
            ) {
                onPlay()
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            DashboardCard(
                title = "Ranking Global",
                description = "Consulta los mejores puntajes."
            ) {
                onRanking()
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            DashboardCard(
                title = "Historial",
                description = "Revisa partidas anteriores."
            ) {
                onHistory()
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            DashboardCard(
                title = "Perfil",
                description = "Estadísticas personales."
            ) {
                onProfile()
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            DashboardCard(
                title = "Ajustes",
                description = "Configuración general."
            ) {
                onSettings()
            }
        }
    }
}