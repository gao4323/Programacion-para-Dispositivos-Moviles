package com.example.prograquiz.ui.screens.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.prograquiz.data.mock.MockData
import com.example.prograquiz.ui.components.AppTopBar

@Composable
fun HistoryScreen() {

    Scaffold(

        topBar = {
            AppTopBar("Historial")
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            items(MockData.history) { match ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            "Fecha: ${match.date}"
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            "Nivel: ${match.level}"
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            "Puntaje: ${match.score}"
                        )
                    }
                }
            }
        }
    }
}