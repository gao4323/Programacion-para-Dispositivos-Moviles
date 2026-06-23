package com.example.prograquiz.navigation
import androidx.compose.runtime.Composable

import androidx.navigation.compose.*

import com.example.prograquiz.ui.screens.home.HomeScreen
import com.example.prograquiz.ui.screens.login.LoginScreen
import com.example.prograquiz.ui.screens.register.RegisterScreen
import com.example.prograquiz.ui.screens.splash.SplashScreen

import androidx.compose.runtime.*

import com.example.prograquiz.model.Question
import com.example.prograquiz.ui.screens.levels.LevelsScreen
import com.example.prograquiz.ui.screens.quiz.QuizScreen
import com.example.prograquiz.ui.screens.feedback.FeedbackScreen
import com.example.prograquiz.ui.screens.result.ResultScreen

import com.example.prograquiz.ui.screens.ranking.RankingScreen
import com.example.prograquiz.ui.screens.history.HistoryScreen
import com.example.prograquiz.ui.screens.profile.ProfileScreen
import com.example.prograquiz.ui.screens.settings.SettingsScreen

@Composable
fun AppNavGraph() {

    var lastQuestion by remember {
        mutableStateOf<Question?>(null)
    }

    var lastAnswer by remember {
        mutableIntStateOf(-1)
    }

    val navController =
        rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash

    ) {
        composable(Routes.Ranking) {

            RankingScreen()
        }

        composable(Routes.History) {

            HistoryScreen()
        }

        composable(Routes.Profile) {

            ProfileScreen()
        }

        composable(Routes.Settings) {

            SettingsScreen()
        }
        composable(Routes.Levels) {

            LevelsScreen {

                navController.navigate(
                    Routes.Quiz
                )
            }
        }

        composable(Routes.Quiz) {

            QuizScreen { question, answer ->

                lastQuestion = question
                lastAnswer = answer

                navController.navigate(
                    Routes.Feedback
                )
            }
        }

        composable(Routes.Feedback) {

            val question = lastQuestion

            if (question != null) {

                FeedbackScreen(

                    isCorrect =
                        lastAnswer ==
                                question.correctAnswer,

                    explanation =
                        question.explanation,

                    onContinue = {

                        navController.navigate(
                            Routes.Result
                        )
                    }
                )
            }
        }

        composable(Routes.Result) {

            ResultScreen(

                score = 95,

                correct = 8,

                incorrect = 2,

                onPlayAgain = {

                    navController.navigate(
                        Routes.Levels
                    )
                },

                onHome = {

                    navController.navigate(
                        Routes.Home
                    )
                }
            )
        }

        composable(Routes.Splash) {

            SplashScreen {

                navController.navigate(
                    Routes.Login
                ) {
                    popUpTo(0)
                }
            }
        }

        composable(Routes.Login) {

            LoginScreen(

                onLogin = {
                    navController.navigate(
                        Routes.Home
                    )
                },

                onRegister = {
                    navController.navigate(
                        Routes.Register
                    )
                }
            )
        }

        composable(Routes.Register) {

            RegisterScreen(

                onRegisterSuccess = {
                    navController.navigate(
                        Routes.Home
                    )
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.Home) {

            HomeScreen(

                onPlay = {
                    navController.navigate(
                        Routes.Levels
                    )
                },

                onRanking = {
                    navController.navigate(
                        Routes.Ranking
                    )
                },

                onHistory = {
                    navController.navigate(
                        Routes.History
                    )
                },

                onProfile = {
                    navController.navigate(
                        Routes.Profile
                    )
                },

                onSettings = {
                    navController.navigate(
                        Routes.Settings
                    )
                }
            )
        }
    }
}