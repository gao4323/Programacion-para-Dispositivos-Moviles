package com.example.prograquiz.ui.screens.ranking


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
fun RankingScreen() {

    Scaffold(

        topBar = {
            AppTopBar("Ranking Global")
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            items(MockData.ranking) { user ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Text(
                            "#${user.position}"
                        )

                        Text(
                            user.username
                        )

                        Text(
                            "${user.score} pts"
                        )
                    }
                }
            }
        }
    }
}