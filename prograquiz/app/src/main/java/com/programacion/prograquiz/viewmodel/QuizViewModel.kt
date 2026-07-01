package com.programacion.prograquiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacion.prograquiz.data.mock.MockData
import com.programacion.prograquiz.model.DifficultyLevel
import com.programacion.prograquiz.model.QuizState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val _quizState = MutableStateFlow<QuizState?>(null)
    val quizState: StateFlow<QuizState?> = _quizState.asStateFlow()

    private var currentLevel: DifficultyLevel = DifficultyLevel.BASICO

    private val _timerSeconds  = MutableStateFlow(TIMER_SECONDS)
    val timerSeconds: StateFlow<Int> = _timerSeconds.asStateFlow()

    private val _timerProgress = MutableStateFlow(1f)
    val timerProgress: StateFlow<Float> = _timerProgress.asStateFlow()

    private val _timerExpired = MutableStateFlow(false)
    val timerExpired: StateFlow<Boolean> = _timerExpired.asStateFlow()

    private var timerJob: Job? = null

    fun startQuiz(level: DifficultyLevel) {
        currentLevel     = level
        _quizState.value = QuizState(questions = MockData.getQuestionsByLevel(level))
        startTimer()
    }

    fun selectOption(index: Int) {
        if (_quizState.value?.answered == true) return
        _quizState.update { it?.copy(selectedOptionIndex = index) }
    }

    fun confirmAnswer(): Boolean {
        timerJob?.cancel()
        _timerExpired.value = false
        val state     = _quizState.value ?: return false
        if (state.answered) return state.selectedOptionIndex == state.currentQuestion.correctIndex
        val selection = state.selectedOptionIndex
        val isCorrect = selection == state.currentQuestion.correctIndex
        _quizState.update { s ->
            s?.copy(
                answered            = true,
                selectedOptionIndex = selection ?: -1,
                correctCount        = if (isCorrect) s.correctCount + 1 else s.correctCount,
                wrongCount          = if (!isCorrect) s.wrongCount + 1 else s.wrongCount
            )
        }
        return isCorrect
    }

    fun nextQuestion() {
        _quizState.update { s ->
            s?.copy(currentIndex = s.currentIndex + 1, selectedOptionIndex = null, answered = false)
        }
        startTimer()
    }

    fun resetQuiz() {
        timerJob?.cancel()
        _quizState.value     = null
        _timerSeconds.value  = TIMER_SECONDS
        _timerProgress.value = 1f
        _timerExpired.value  = false
    }

    fun getCurrentLevel(): DifficultyLevel = currentLevel

    fun getFinalStats(): Triple<Int, Int, Int> {
        val s = _quizState.value ?: return Triple(0, 0, 5)
        return Triple(s.finalScore, s.correctCount, s.questions.size)
    }

    private fun startTimer() {
        timerJob?.cancel()
        _timerSeconds.value  = TIMER_SECONDS
        _timerProgress.value = 1f
        _timerExpired.value  = false
        timerJob = viewModelScope.launch {
            for (remaining in (TIMER_SECONDS - 1) downTo 0) {
                delay(1_000L)
                _timerSeconds.value  = remaining
                _timerProgress.value = remaining.toFloat() / TIMER_SECONDS.toFloat()
            }
            val s = _quizState.value
            if (s != null && !s.answered) _timerExpired.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    companion object { const val TIMER_SECONDS = 30 }
}
