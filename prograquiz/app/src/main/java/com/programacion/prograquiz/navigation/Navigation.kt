package com.programacion.prograquiz.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.programacion.prograquiz.model.DifficultyLevel
import com.programacion.prograquiz.model.GameHistory
import com.programacion.prograquiz.ui.screens.*
import com.programacion.prograquiz.viewmodel.QuizViewModel
import com.programacion.prograquiz.viewmodel.SessionViewModel

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
        fun createRoute(isCorrect: Boolean, questionIndex: Int) = "feedback/$isCorrect/$questionIndex"
    }
    object Result      : Screen("result/{score}/{correct}/{total}/{level}") {
        fun createRoute(score: Int, correct: Int, total: Int, level: String) =
            "result/$score/$correct/$total/$level"
    }
    object Ranking  : Screen("ranking")
    object History  : Screen("history")
    object Profile  : Screen("profile")
}

@Composable
fun PrograQuizNavHost(navController: NavHostController, sessionViewModel: SessionViewModel) {

    val quizViewModel: QuizViewModel = viewModel()

    NavHost(
        navController    = navController,
        startDestination = Screen.Splash.route,
        enterTransition  = { fadeIn(tween(250)) },
        exitTransition   = { fadeOut(tween(200)) }
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(onNavigateToLogin = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Login.route) {
            LoginScreen(
                sessionViewModel     = sessionViewModel,
                onLoginSuccess       = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                sessionViewModel  = sessionViewModel,
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                sessionViewModel        = sessionViewModel,
                onNavigateToLevelSelect = { navController.navigate(Screen.LevelSelect.route) },
                onNavigateToRanking     = { navController.navigate(Screen.Ranking.route) },
                onNavigateToHistory     = { navController.navigate(Screen.History.route) },
                onNavigateToProfile     = { navController.navigate(Screen.Profile.route) },
                onNavigateToSettings    = { }
            )
        }

        composable(Screen.LevelSelect.route) {
            LevelSelectScreen(
                onLevelSelected = { level ->
                    quizViewModel.startQuiz(level)
                    navController.navigate(Screen.Quiz.createRoute(level.name))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

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
                },
                onNavigateBack = {
                    quizViewModel.resetQuiz()
                    navController.popBackStack()
                }
            )
        }

        composable(
            route     = Screen.Feedback.route,
            arguments = listOf(
                navArgument("isCorrect")     { type = NavType.BoolType },
                navArgument("questionIndex") { type = NavType.IntType }
            )
        ) { back ->
            FeedbackScreen(
                isCorrect     = back.arguments?.getBoolean("isCorrect") ?: false,
                questionIndex = back.arguments?.getInt("questionIndex") ?: 0,
                viewModel     = quizViewModel,
                onNext = {
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

        composable(
            route     = Screen.Result.route,
            arguments = listOf(
                navArgument("score")   { type = NavType.IntType },
                navArgument("correct") { type = NavType.IntType },
                navArgument("total")   { type = NavType.IntType },
                navArgument("level")   { type = NavType.StringType }
            )
        ) { back ->
            ResultScreen(
                score   = back.arguments?.getInt("score")    ?: 0,
                correct = back.arguments?.getInt("correct")  ?: 0,
                total   = back.arguments?.getInt("total")    ?: 5,
                level   = DifficultyLevel.valueOf(back.arguments?.getString("level") ?: "BASICO"),
                onPlayAgain = {
                    quizViewModel.resetQuiz()
                    navController.navigate(Screen.LevelSelect.route) { popUpTo(Screen.Home.route) }
                },
                onGoHome = {
                    quizViewModel.resetQuiz()
                    navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) { inclusive = true } }
                }
            )
        }

        composable(Screen.Ranking.route) {
            RankingScreen(
                sessionViewModel = sessionViewModel,
                onNavigateBack   = { navController.popBackStack() }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                sessionViewModel = sessionViewModel,
                onNavigateBack   = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                sessionViewModel    = sessionViewModel,
                onNavigateBack      = { navController.popBackStack() },
                onNavigateToHistory = { navController.navigate(Screen.History.route) },
                onLogout            = {
                    sessionViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
