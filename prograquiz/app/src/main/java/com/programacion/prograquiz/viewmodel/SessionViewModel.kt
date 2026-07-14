package com.programacion.prograquiz.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.programacion.prograquiz.data.db.AppDatabase
import com.programacion.prograquiz.data.repository.UserRepository
import com.programacion.prograquiz.model.GameHistory
import com.programacion.prograquiz.model.RankingEntry
import com.programacion.prograquiz.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ViewModel de sesión con persistencia real mediante Room.
 * Hereda de AndroidViewModel para tener acceso al Context y crear la BD.
 */
class SessionViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = UserRepository(AppDatabase.getInstance(application))

    // ── Estado de sesión ──────────────────────────────────────────────────────

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    // ── Historial ─────────────────────────────────────────────────────────────

    private val _history = MutableStateFlow<List<GameHistory>>(emptyList())
    val history: StateFlow<List<GameHistory>> = _history.asStateFlow()

    // ── Ranking ───────────────────────────────────────────────────────────────

    private val _ranking = MutableStateFlow<List<RankingEntry>>(emptyList())
    val ranking: StateFlow<List<RankingEntry>> = _ranking.asStateFlow()

    // ── Mensajes de error / UI ────────────────────────────────────────────────

    private val _loginError    = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    private val _registerError = MutableStateFlow<String?>(null)
    val registerError: StateFlow<String?> = _registerError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ── Login ─────────────────────────────────────────────────────────────────

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            _loginError.value = "Completa todos los campos"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            val result = repo.login(email, password)
            _isLoading.value = false
            when (result) {
                is UserRepository.AuthResult.Success -> {
                    _currentUser.value = result.user
                    _isLoggedIn.value  = true
                    _loginError.value  = null
                    loadUserData(result.user.id.toInt())
                    onSuccess()
                }
                is UserRepository.AuthResult.Error -> {
                    _loginError.value = result.message
                }
            }
        }
    }

    fun clearLoginError()    { _loginError.value = null }
    fun clearRegisterError() { _registerError.value = null }

    // ── Registro ──────────────────────────────────────────────────────────────

    fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        if (password != confirmPassword) {
            _registerError.value = "Las contraseñas no coinciden"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            val result = repo.register(username, email, password)
            _isLoading.value = false
            when (result) {
                is UserRepository.AuthResult.Success -> {
                    _currentUser.value  = result.user
                    _isLoggedIn.value   = true
                    _registerError.value = null
                    loadUserData(result.user.id.toInt())
                    onSuccess()
                }
                is UserRepository.AuthResult.Error -> {
                    _registerError.value = result.message
                }
            }
        }
    }

    // ── Logout ────────────────────────────────────────────────────────────────

    fun logout() {
        _currentUser.value = null
        _isLoggedIn.value  = false
        _history.value     = emptyList()
    }

    // ── Registrar partida ─────────────────────────────────────────────────────

    fun recordGame(game: GameHistory) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            // Guardar en BD
            repo.saveGame(user.id.toInt(), game)
            // Actualizar usuario en memoria
            val updated = user.copy(
                bestScore      = maxOf(user.bestScore, game.score),
                totalGames     = user.totalGames + 1,
                totalCorrect   = user.totalCorrect + game.correct,
                totalQuestions = user.totalQuestions + game.total
            )
            _currentUser.value = updated
            // Persistir stats en BD
            repo.updateStats(updated)
            // Refrescar historial y ranking
            loadUserData(user.id.toInt())
        }
    }

    // ── Cargar datos del usuario ──────────────────────────────────────────────

    private fun loadUserData(userId: Int) {
        viewModelScope.launch {
            // Historial
            _history.value = repo.getHistoryOnce(userId)
            // Ranking
            _ranking.value = repo.getRankingOnce()
        }
    }

    fun refreshRanking() {
        viewModelScope.launch {
            _ranking.value = repo.getRankingOnce()
        }
    }

    // ── Util ──────────────────────────────────────────────────────────────────

    fun currentTimestamp(): String =
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("es", "PE")).format(Date())
}
