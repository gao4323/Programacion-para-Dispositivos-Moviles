package com.programacion.prograquiz.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :hash LIMIT 1")
    suspend fun login(email: String, hash: String): UserEntity?

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun emailExists(email: String): Int

    @Query("""
        UPDATE users SET
            bestScore      = :bestScore,
            totalGames     = :totalGames,
            totalCorrect   = :totalCorrect,
            totalQuestions = :totalQuestions
        WHERE id = :id
    """)
    suspend fun updateStats(
        id: Int, bestScore: Int, totalGames: Int,
        totalCorrect: Int, totalQuestions: Int
    )
}

@Dao
interface GameHistoryDao {

    @Insert
    suspend fun insert(game: GameHistoryEntity)

    @Query("SELECT * FROM game_history WHERE userId = :userId ORDER BY id DESC LIMIT 20")
    fun getByUser(userId: Int): Flow<List<GameHistoryEntity>>

    @Query("SELECT * FROM game_history WHERE userId = :userId ORDER BY id DESC LIMIT 20")
    suspend fun getByUserOnce(userId: Int): List<GameHistoryEntity>
}

@Dao
interface RankingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: RankingEntity)

    @Query("SELECT * FROM ranking ORDER BY bestScore DESC LIMIT 10")
    fun getTop10(): Flow<List<RankingEntity>>

    @Query("SELECT * FROM ranking ORDER BY bestScore DESC LIMIT 10")
    suspend fun getTop10Once(): List<RankingEntity>
}
