package com.example.prograquiz.ui.screens.settings

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.prograquiz.ui.components.AppTopBar

@Composable
fun SettingsScreen() {

    var notifications by remember {
        mutableStateOf(true)
    }

    Scaffold(

        topBar = {
            AppTopBar("Configuración")
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        "Preferencias"
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Text(
                            "Notificaciones"
                        )

                        Switch(
                            checked = notifications,
                            onCheckedChange = {
                                notifications = it
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text("Versión")

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text("PrograQuiz v1.0")
                }
            }
        }
    }
}