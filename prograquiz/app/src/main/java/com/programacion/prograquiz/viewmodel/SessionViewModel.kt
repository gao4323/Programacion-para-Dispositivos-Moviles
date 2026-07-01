package com.programacion.prograquiz.viewmodel

import androidx.lifecycle.ViewModel
import com.programacion.prograquiz.data.mock.MockData
import com.programacion.prograquiz.model.GameHistory
import com.programacion.prograquiz.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class SessionViewModel : ViewModel() {

    companion object {
        private const val VALID_EMAIL    = "gjaraz@ulasalle.edu.pe"
        private const val VALID_PASSWORD = "123"
    }

    // Sesión

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // Historial
    private val _history = MutableStateFlow<List<GameHistory>>(MockData.gameHistory)
    val history: StateFlow<List<GameHistory>> = _history.asStateFlow()

    //  Login
    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    /** Verifica las credenciales fijas del proyecto. */
    fun login(email: String, password: String, onSuccess: () -> Unit) {
        if (email.trim() == VALID_EMAIL && password == VALID_PASSWORD) {
            _currentUser.value = MockData.currentUser
            _isLoggedIn.value  = true
            _loginError.value  = null
            onSuccess()
        } else {
            _loginError.value = "Correo o contraseña incorrectos"
        }
    }

    fun clearLoginError() {
        _loginError.value = null
    }

    fun logout() {
        _isLoggedIn.value  = false
        _currentUser.value = null
        _history.value     = MockData.gameHistory
    }

    //  Registrar partida jugada
    fun recordGame(game: GameHistory) {
        _history.update { current -> listOf(game) + current }
        _currentUser.update { user ->
            user?.copy(
                bestScore      = maxOf(user.bestScore, game.score),
                totalGames     = user.totalGames + 1,
                totalCorrect   = user.totalCorrect + game.correct,
                totalQuestions = user.totalQuestions + game.total
            )
        }
    }

    fun currentTimestamp(): String =
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("es", "PE")).format(Date())
}
