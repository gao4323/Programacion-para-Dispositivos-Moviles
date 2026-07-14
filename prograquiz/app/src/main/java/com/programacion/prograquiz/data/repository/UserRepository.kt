package com.programacion.prograquiz.data.repository

import com.programacion.prograquiz.data.db.AppDatabase
import com.programacion.prograquiz.data.db.GameHistoryEntity
import com.programacion.prograquiz.data.db.RankingEntity
import com.programacion.prograquiz.data.db.UserEntity
import com.programacion.prograquiz.model.DifficultyLevel
import com.programacion.prograquiz.model.GameHistory
import com.programacion.prograquiz.model.RankingEntry
import com.programacion.prograquiz.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.MessageDigest

class UserRepository(db: AppDatabase) {

    private val userDao    = db.userDao()
    private val historyDao = db.gameHistoryDao()
    private val rankingDao = db.rankingDao()

    // ── Auth ──────────────────────────────────────────────────────────────────

    sealed class AuthResult {
        data class Success(val user: User) : AuthResult()
        data class Error(val message: String) : AuthResult()
    }

    suspend fun register(username: String, email: String, password: String): AuthResult {
        if (username.isBlank())
            return AuthResult.Error("El nombre no puede estar vacío")
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return AuthResult.Error("Correo inválido")
        if (password.length < 4)
            return AuthResult.Error("La contraseña debe tener al menos 4 caracteres")
        if (userDao.emailExists(email.trim().lowercase()) > 0)
            return AuthResult.Error("Ese correo ya está registrado")

        val entity = UserEntity(
            username     = username.trim(),
            email        = email.trim().lowercase(),
            passwordHash = md5(password)
        )
        val newId = userDao.insert(entity).toInt()
        rankingDao.upsert(
            RankingEntity(
                userId    = newId,
                username  = username.trim(),
                bestScore = 0,
                bestLevel = DifficultyLevel.BASICO.name
            )
        )
        return AuthResult.Success(entity.copy(id = newId).toUser())
    }

    suspend fun login(email: String, password: String): AuthResult {
        val entity = userDao.login(email.trim().lowercase(), md5(password))
            ?: return AuthResult.Error("Correo o contraseña incorrectos")
        return AuthResult.Success(entity.toUser())
    }

    suspend fun updateStats(user: User) {
        userDao.updateStats(
            id             = user.id.toInt(),
            bestScore      = user.bestScore,
            totalGames     = user.totalGames,
            totalCorrect   = user.totalCorrect,
            totalQuestions = user.totalQuestions
        )
        rankingDao.upsert(
            RankingEntity(
                userId    = user.id.toInt(),
                username  = user.username,
                bestScore = user.bestScore,
                bestLevel = user.favoriteLevel.name
            )
        )
    }

    // ── History ───────────────────────────────────────────────────────────────

    suspend fun saveGame(userId: Int, game: GameHistory) {
        historyDao.insert(
            GameHistoryEntity(
                userId  = userId,
                date    = game.date,
                level   = game.level.name,
                score   = game.score,
                correct = game.correct,
                total   = game.total
            )
        )
    }

    fun getHistoryFlow(userId: Int): Flow<List<GameHistory>> =
        historyDao.getByUser(userId).map { list -> list.map { it.toGameHistory() } }

    suspend fun getHistoryOnce(userId: Int): List<GameHistory> =
        historyDao.getByUserOnce(userId).map { it.toGameHistory() }

    // ── Ranking ───────────────────────────────────────────────────────────────

    fun getRankingFlow(): Flow<List<RankingEntry>> =
        rankingDao.getTop10().map { list ->
            list.mapIndexed { idx, it ->
                RankingEntry(
                    position       = idx + 1,
                    username       = it.username,
                    score          = it.bestScore,
                    level          = safeLevelOf(it.bestLevel),
                    avatarInitials = it.username.take(2).uppercase()
                )
            }
        }

    suspend fun getRankingOnce(): List<RankingEntry> =
        rankingDao.getTop10Once().mapIndexed { idx, it ->
            RankingEntry(
                position       = idx + 1,
                username       = it.username,
                score          = it.bestScore,
                level          = safeLevelOf(it.bestLevel),
                avatarInitials = it.username.take(2).uppercase()
            )
        }

    // ── Converters ────────────────────────────────────────────────────────────

    private fun UserEntity.toUser() = User(
        id             = id.toString(),
        username       = username,
        email          = email,
        avatarInitials = username.take(2).uppercase(),
        bestScore      = bestScore,
        totalGames     = totalGames,
        totalCorrect   = totalCorrect,
        totalQuestions = totalQuestions,
        favoriteLevel  = DifficultyLevel.BASICO
    )

    private fun GameHistoryEntity.toGameHistory() = GameHistory(
        id      = id,
        date    = date,
        level   = safeLevelOf(level),
        score   = score,
        correct = correct,
        total   = total
    )

    private fun safeLevelOf(name: String): DifficultyLevel =
        runCatching { DifficultyLevel.valueOf(name) }.getOrDefault(DifficultyLevel.BASICO)

    // ── Util ──────────────────────────────────────────────────────────────────

    private fun md5(input: String): String {
        val bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
