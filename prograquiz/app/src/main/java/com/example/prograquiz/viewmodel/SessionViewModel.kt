package com.example.prograquiz.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.prograquiz.data.mock.MockData
import com.example.prograquiz.model.GameHistory
import com.example.prograquiz.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Activity-scoped ViewModel that owns:
 *  - Auth session (mock)
 *  - Game history list (grows in-memory during the session)
 *  - App settings toggles
 *  - Form validation states for Login & Register
 */
class SessionViewModel : ViewModel() {

    // ── Auth ──────────────────────────────────────────────────────────────────

    private val _isLoggedIn   = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentUser  = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // ── History ───────────────────────────────────────────────────────────────

    private val _history = MutableStateFlow<List<GameHistory>>(MockData.gameHistory)
    val history: StateFlow<List<GameHistory>> = _history.asStateFlow()

    // ── Settings ──────────────────────────────────────────────────────────────

    data class AppSettings(
        val soundEnabled: Boolean       = true,
        val vibrationEnabled: Boolean   = true,
        val notificationsEnabled: Boolean = false,
        val darkMode: Boolean           = true
    )

    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    // ── Form states ───────────────────────────────────────────────────────────

    data class LoginForm(
        val emailError: String?    = null,
        val passwordError: String? = null,
        val generalError: String?  = null,
        val isLoading: Boolean     = false
    )

    data class RegisterForm(
        val usernameError: String? = null,
        val emailError: String?    = null,
        val passwordError: String? = null,
        val confirmError: String?  = null,
        val isLoading: Boolean     = false
    )

    private val _loginForm    = MutableStateFlow(LoginForm())
    val loginForm: StateFlow<LoginForm> = _loginForm.asStateFlow()

    private val _registerForm = MutableStateFlow(RegisterForm())
    val registerForm: StateFlow<RegisterForm> = _registerForm.asStateFlow()

    // ── Auth actions ──────────────────────────────────────────────────────────

    /** Validate then mock-authenticate. Calls [onSuccess] if valid. */
    fun login(email: String, password: String, onSuccess: () -> Unit) {
        val emailErr    = validateEmail(email)
        val passwordErr = when {
            password.isBlank()  -> "La contraseña no puede estar vacía"
            password.length < 6 -> "Mínimo 6 caracteres"
            else -> null
        }
        if (emailErr != null || passwordErr != null) {
            _loginForm.value = LoginForm(emailError = emailErr, passwordError = passwordErr)
            return
        }
        // Mock: any valid-format credentials succeed
        _currentUser.value = MockData.currentUser
        _isLoggedIn.value  = true
        _loginForm.value   = LoginForm()
        onSuccess()
    }

    fun demoLogin(onSuccess: () -> Unit) {
        _currentUser.value = MockData.currentUser
        _isLoggedIn.value  = true
        onSuccess()
    }

    fun clearLoginError() {
        _loginForm.update { it.copy(emailError = null, passwordError = null, generalError = null) }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        val usernameErr = when {
            username.isBlank()  -> "El nombre no puede estar vacío"
            username.length < 3 -> "Mínimo 3 caracteres"
            else -> null
        }
        val emailErr    = validateEmail(email)
        val passwordErr = if (password.length < 6) "Mínimo 6 caracteres" else null
        val confirmErr  = if (password != confirmPassword) "Las contraseñas no coinciden" else null

        if (usernameErr != null || emailErr != null || passwordErr != null || confirmErr != null) {
            _registerForm.value = RegisterForm(
                usernameError = usernameErr,
                emailError    = emailErr,
                passwordError = passwordErr,
                confirmError  = confirmErr
            )
            return
        }
        val newUser = MockData.currentUser.copy(
            username       = username,
            email          = email,
            avatarInitials = username.take(2).uppercase(),
            bestScore      = 0,
            totalGames     = 0,
            totalCorrect   = 0,
            totalQuestions = 0
        )
        _currentUser.value  = newUser
        _isLoggedIn.value   = true
        _registerForm.value = RegisterForm()
        onSuccess()
    }

    fun logout() {
        _isLoggedIn.value  = false
        _currentUser.value = null
        _loginForm.value   = LoginForm()
        // Reset history to mock on logout
        _history.value     = MockData.gameHistory
    }

    // ── Game recording ────────────────────────────────────────────────────────

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

    // ── Settings ──────────────────────────────────────────────────────────────

    fun toggleSound()          { _settings.update { it.copy(soundEnabled          = !it.soundEnabled) } }
    fun toggleVibration()      { _settings.update { it.copy(vibrationEnabled      = !it.vibrationEnabled) } }
    fun toggleNotifications()  { _settings.update { it.copy(notificationsEnabled  = !it.notificationsEnabled) } }
    fun toggleDarkMode()       { _settings.update { it.copy(darkMode              = !it.darkMode) } }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun validateEmail(email: String): String? = when {
        email.isBlank() -> "El correo no puede estar vacío"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Correo con formato inválido"
        else -> null
    }

    fun currentTimestamp(): String =
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("es", "PE")).format(Date())
}
