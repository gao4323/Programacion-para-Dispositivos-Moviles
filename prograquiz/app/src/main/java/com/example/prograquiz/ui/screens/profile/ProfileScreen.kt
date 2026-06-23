package com.example.prograquiz.ui.screens.profile

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.prograquiz.data.mock.MockData
import com.example.prograquiz.ui.components.AppTopBar
import com.example.prograquiz.ui.components.StatCard

@Composable
fun ProfileScreen() {

    val user = MockData.currentUser

    Scaffold(

        topBar = {
            AppTopBar("Perfil")
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
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Text(
                        text = user.name,
                        style =
                            MaterialTheme.typography.headlineSmall
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = user.email
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceEvenly
            ) {

                StatCard(
                    "Mejor",
                    user.bestScore.toString()
                )

                StatCard(
                    "Partidas",
                    user.matchesPlayed.toString()
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            StatCard(
                "Precisión",
                "${user.accuracy}%"
            )
        }
    }
}