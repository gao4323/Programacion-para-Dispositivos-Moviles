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

// Controlar toda la lógica del juego
class GameViewModel(application: Application) : AndroidViewModel(application) {

    // Lista de colores
    private val colors = listOf(
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Yellow
    )

    // Lista de nombres de cada color
    private val colorNames = listOf(
        "Rojo",
        "Azul",
        "Verde",
        "Amarillo"
    )

    // Color mostrado
    private val _currentColor = MutableStateFlow(Color.Red)
    val currentColor = _currentColor.asStateFlow()

    // Nombre del color
    private val _currentColorName = MutableStateFlow("Rojo")
    val currentColorName = _currentColorName.asStateFlow()

    // Puntaje del jugador
    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    // Tiempo restante
    private val _timeLeft = MutableStateFlow(30)
    val timeLeft = _timeLeft.asStateFlow()

    // Mensaje de respuesta
    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    // Historial de partidas
    private val _history = MutableStateFlow<List<ScoreHistory>>(emptyList())
    val history = _history.asStateFlow()

    // Iniciar el juego
    fun startGame() {

        _score.value = 0
        _timeLeft.value = 15
        changeColor()

        viewModelScope.launch {

            while (_timeLeft.value > 0) {
                delay(1000)
                _timeLeft.value--
            }

            saveScore()
            _message.value = "FIN"
        }
    }

    // Verifica si el color seleccionado es correcto
    fun selectColor(color: Color) {
        if (color == _currentColor.value) {

            _score.value++
            _message.value = "Correcto"

        } else {

            _message.value = "Incorrecto"
        }
        changeColor()
    }

    // Seleccionar un nuevo color aleatorio
    private fun changeColor() {

        val index = Random.nextInt(colors.size)
        _currentColor.value = colors[index]
        _currentColorName.value = colorNames[index]
    }

    // Guardar el puntaje
    private fun saveScore() {

        val list = _history.value.toMutableList()

        // Agrega una nueva partida al historial
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

        // Si el puntaje actual supera el récord
        if (_score.value > highScore) {
            shared.edit()
                .putInt("high_score", _score.value)
                .apply()
        }
    }

    // Obtiene el puntaje más alto guardado
    fun getHighScore(): Int {

        val shared =
            getApplication<Application>()
                .getSharedPreferences("game_data", Context.MODE_PRIVATE)

        return shared.getInt("high_score", 0)
    }
}