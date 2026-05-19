package com.example.parcial

import android.app.Application
import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val colors = listOf(
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Yellow
    )

    private val colorNames = listOf(
        "Rojo",
        "Azul",
        "Verde",
        "Amarillo"
    )

    private val _currentColor = MutableStateFlow(Color.Red)
    val currentColor = _currentColor.asStateFlow()
    private val _currentColorName = MutableStateFlow("Rojo")
    val currentColorName = _currentColorName.asStateFlow()
    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()
    private val _timeLeft = MutableStateFlow(30)
    val timeLeft = _timeLeft.asStateFlow()
    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()
    private val _history = MutableStateFlow<List<ScoreHistory>>(emptyList())
    val history = _history.asStateFlow()

    fun startGame() {

        _score.value = 0
        _timeLeft.value = 30

        changeColor()

        viewModelScope.launch {

            while (_timeLeft.value > 0) {
                delay(1000)
                _timeLeft.value--
            }

            saveScore()
        }
    }

    fun selectColor(color: Color) {

        if (color == _currentColor.value) {
            _score.value++
            _message.value = "Correcto"
        } else {
            _message.value = "Incorrecto"
        }

        changeColor()
    }

    private fun changeColor() {

        val index = Random.nextInt(colors.size)

        _currentColor.value = colors[index]
        _currentColorName.value = colorNames[index]
    }

    private fun saveScore() {

        val list = _history.value.toMutableList()

        list.add(
            ScoreHistory(
                gameNumber = list.size + 1,
                score = _score.value
            )
        )

        _history.value = list

        val shared =
            getApplication<Application>()
                .getSharedPreferences("game_data", Context.MODE_PRIVATE)

        val highScore = shared.getInt("high_score", 0)

        if (_score.value > highScore) {
            shared.edit()
                .putInt("high_score", _score.value)
                .apply()
        }
    }

    fun getHighScore(): Int {

        val shared =
            getApplication<Application>()
                .getSharedPreferences("game_data", Context.MODE_PRIVATE)

        return shared.getInt("high_score", 0)
    }
}