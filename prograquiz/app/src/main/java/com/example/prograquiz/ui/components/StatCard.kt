package com.example.prograquiz.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatCard(
    title: String,
    value: String
) {

    Card {

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text(
                title,
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                value,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}