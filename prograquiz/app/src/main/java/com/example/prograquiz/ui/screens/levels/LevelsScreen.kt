package com.example.prograquiz.ui.screens.levels

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.prograquiz.data.mock.MockData
import com.example.prograquiz.ui.components.AppTopBar
import com.example.prograquiz.ui.components.LevelCard

@Composable
fun LevelsScreen(
    onStartQuiz: () -> Unit
) {

    Scaffold(

        topBar = {
            AppTopBar("Seleccionar Nivel")
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

            MockData.levels.forEach { level ->

                LevelCard(
                    title = level.title,
                    description = level.description
                ) {
                    onStartQuiz()
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
        }
    }
}