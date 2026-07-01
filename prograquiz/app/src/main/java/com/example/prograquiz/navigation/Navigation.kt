package com.example.prograquiz.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.prograquiz.model.DifficultyLevel
import com.example.prograquiz.model.GameHistory
import com.example.prograquiz.ui.screens.*
import com.example.prograquiz.viewmodel.QuizViewModel
import com.example.prograquiz.viewmodel.SessionViewModel

// ── Route constants ───────────────────────────────────────────────────────────

sealed class Screen(val route: String) {
    object Splash      : Screen("splash")
    object Login       : Screen("login")
    object Register    : Screen("register")
    object Home        : Screen("home")
    object LevelSelect : Screen("level_select")
    object Quiz        : Screen("quiz/{level}") {
        fun createRoute(level: String) = "quiz/$level"
    }
    object Feedback    : Screen("feedback/{isCorrect}/{questionIndex}") {
        fun createRoute(isCorrect: Boolean, questionIndex: Int) =
            "feedback/$isCorrect/$questionIndex"
    }
    object Result      : Screen("result/{score}/{correct}/{total}/{level}") {
        fun createRoute(score: Int, correct: Int, total: Int, level: String) =
            "result/$score/$correct/$total/$level"
    }
    object Ranking     : Screen("ranking")
    object History     : Screen("history")
    object Profile     : Screen("profile")
    object Settings    : Screen("settings")
}

// ── Animation specs ───────────────────────────────────────────────────────────

private val enterSlide = slideInHorizontally(tween(300)) { it } + fadeIn(tween(300))
private val exitSlide  = slideOutHorizontally(tween(300)) { -it } + fadeOut(tween(300))
private val popEnter   = slideInHorizontally(tween(300)) { -it } + fadeIn(tween(300))
private val popExit    = slideOutHorizontally(tween(300)) { it } + fadeOut(tween(300))

private val fadeEnter  = fadeIn(tween(400))
private val fadeExit   = fadeOut(tween(300))

// ── Nav Graph ────────────────────────────────────────────────────────────────

@Composable
fun PrograQuizNavHost(
    navController: NavHostController,
    sessionViewModel: SessionViewModel
) {
    val quizViewModel: QuizViewModel = viewModel()

    NavHost(
        navController    = navController,
        startDestination = Screen.Splash.route,
        enterTransition  = { enterSlide },
        exitTransition   = { exitSlide },
        popEnterTransition = { popEnter },
        popExitTransition  = { popExit }
    ) {

        // ── Splash ────────────────────────────────────────────────────────────
        composable(
            route           = Screen.Splash.route,
            enterTransition = { fadeEnter },
            exitTransition  = { fadeExit }
        ) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Login ─────────────────────────────────────────────────────────────
        composable(Screen.Login.route) {
            LoginScreen(
                sessionViewModel    = sessionViewModel,
                onLoginSuccess      = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // ── Register ──────────────────────────────────────────────────────────
        composable(Screen.Register.route) {
            RegisterScreen(
                sessionViewModel = sessionViewModel,
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Home ──────────────────────────────────────────────────────────────
        composable(
            route           = Screen.Home.route,
            enterTransition = { fadeEnter },
            exitTransition  = { fadeExit },
            popEnterTransition = { fadeEnter },
            popExitTransition  = { fadeExit }
        ) {
            HomeScreen(
                sessionViewModel        = sessionViewModel,
                onNavigateToLevelSelect = { navController.navigate(Screen.LevelSelect.route) },
                onNavigateToRanking     = { navController.navigate(Screen.Ranking.route) },
                onNavigateToHistory     = { navController.navigate(Screen.History.route) },
                onNavigateToProfile     = { navController.navigate(Screen.Profile.route) },
                onNavigateToSettings    = { navController.navigate(Screen.Settings.route) }
            )
        }

        // ── Level Select ──────────────────────────────────────────────────────
        composable(Screen.LevelSelect.route) {
            LevelSelectScreen(
                onLevelSelected = { level ->
                    quizViewModel.startQuiz(level)
                    navController.navigate(Screen.Quiz.createRoute(level.name))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Quiz ──────────────────────────────────────────────────────────────
        composable(
            route     = Screen.Quiz.route,
            arguments = listOf(navArgument("level") { type = NavType.StringType })
        ) {
            QuizScreen(
                viewModel         = quizViewModel,
                onAnswerConfirmed = { isCorrect, questionIndex ->
                    navController.navigate(Screen.Feedback.createRoute(isCorrect, questionIndex))
                },
                onQuizFinished = { score, correct, total, level ->
                    // Record game in session before navigating
                    sessionViewModel.recordGame(
                        GameHistory(
                            id     = System.currentTimeMillis().toInt(),
                            date   = sessionViewModel.currentTimestamp(),
                            level  = DifficultyLevel.valueOf(level),
                            score  = score,
                            correct = correct,
                            total  = total
                        )
                    )
                    navController.navigate(Screen.Result.createRoute(score, correct, total, level)) {
                        popUpTo(Screen.LevelSelect.route)
                    }
                },
                onNavigateBack = {
                    quizViewModel.resetQuiz()
                    navController.popBackStack()
                }
            )
        }

        // ── Feedback ──────────────────────────────────────────────────────────
        composable(
            route     = Screen.Feedback.route,
            arguments = listOf(
                navArgument("isCorrect")     { type = NavType.BoolType },
                navArgument("questionIndex") { type = NavType.IntType }
            ),
            enterTransition = { slideInVertically(tween(350)) { it } + fadeIn(tween(350)) },
            exitTransition  = { slideOutVertically(tween(300)) { -it } + fadeOut(tween(300)) }
        ) { back ->
            val isCorrect     = back.arguments?.getBoolean("isCorrect") ?: false
            val questionIndex = back.arguments?.getInt("questionIndex") ?: 0
            FeedbackScreen(
                isCorrect     = isCorrect,
                questionIndex = questionIndex,
                viewModel     = quizViewModel,
                onNext = {
                    // Advance state FIRST so QuizScreen recomposes correctly on return
                    quizViewModel.nextQuestion()
                    navController.popBackStack()
                },
                onFinish = { score, correct, total, level ->
                    sessionViewModel.recordGame(
                        GameHistory(
                            id      = System.currentTimeMillis().toInt(),
                            date    = sessionViewModel.currentTimestamp(),
                            level   = DifficultyLevel.valueOf(level),
                            score   = score,
                            correct = correct,
                            total   = total
                        )
                    )
                    navController.navigate(Screen.Result.createRoute(score, correct, total, level)) {
                        popUpTo(Screen.LevelSelect.route)
                    }
                }
            )
        }

        // ── Result ────────────────────────────────────────────────────────────
        composable(
            route     = Screen.Result.route,
            arguments = listOf(
                navArgument("score")   { type = NavType.IntType },
                navArgument("correct") { type = NavType.IntType },
                navArgument("total")   { type = NavType.IntType },
                navArgument("level")   { type = NavType.StringType }
            ),
            enterTransition = { scaleIn(tween(400), 0.85f) + fadeIn(tween(400)) },
            exitTransition  = { fadeExit }
        ) { back ->
            ResultScreen(
                score   = back.arguments?.getInt("score")    ?: 0,
                correct = back.arguments?.getInt("correct")  ?: 0,
                total   = back.arguments?.getInt("total")    ?: 5,
                level   = DifficultyLevel.valueOf(back.arguments?.getString("level") ?: DifficultyLevel.BASICO.name),
                onPlayAgain = {
                    quizViewModel.resetQuiz()
                    navController.navigate(Screen.LevelSelect.route) {
                        popUpTo(Screen.Home.route)
                    }
                },
                onGoHome = {
                    quizViewModel.resetQuiz()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Ranking ───────────────────────────────────────────────────────────
        composable(Screen.Ranking.route) {
            RankingScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ── History ───────────────────────────────────────────────────────────
        composable(Screen.History.route) {
            HistoryScreen(
                sessionViewModel = sessionViewModel,
                onNavigateBack   = { navController.popBackStack() }
            )
        }

        // ── Profile ───────────────────────────────────────────────────────────
        composable(Screen.Profile.route) {
            ProfileScreen(
                sessionViewModel    = sessionViewModel,
                onNavigateBack      = { navController.popBackStack() },
                onNavigateToHistory = { navController.navigate(Screen.History.route) }
            )
        }

        // ── Settings ──────────────────────────────────────────────────────────
        composable(Screen.Settings.route) {
            SettingsScreen(
                sessionViewModel = sessionViewModel,
                onNavigateBack   = { navController.popBackStack() },
                onLogout = {
                    sessionViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

// ── Helper ────────────────────────────────────────────────────────────────────

@Composable
fun currentRoute(navController: NavHostController): String? {
    val entry by navController.currentBackStackEntryAsState()
    return entry?.destination?.route
}
