package by.freiding.braindrop.feature.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.home.domain.usecase.GetDailyProgressUseCase
import by.freiding.braindrop.feature.home.domain.usecase.GetStudyCategoriesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getStudyCategories: GetStudyCategoriesUseCase,
    private val getDailyProgress: GetDailyProgressUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<HomeUiEffect>()
    val effects: SharedFlow<HomeUiEffect> = _effects.asSharedFlow()

    // The initial load is triggered by the screen (LifecycleResumeEffect { reload() }), which
    // also fires on first composition — loading here too would duplicate the query.

    fun reload() {
        loadCategories()
        loadDailyProgress()
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.CategoryClicked -> viewModelScope.launch {
                _effects.emit(HomeUiEffect.NavigateToCategory(event.categoryId))
            }
            is HomeUiEvent.ContinueQuizClicked -> viewModelScope.launch {
                _effects.emit(HomeUiEffect.ContinueQuiz)
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            when (val result = getStudyCategories()) {
                is Result.Success -> _state.update {
                    it.copy(isLoading = false, categories = result.data)
                }
                is Result.Error -> _state.update {
                    it.copy(isLoading = false, error = result.exception.message)
                }
            }
        }
    }

    private fun loadDailyProgress() {
        viewModelScope.launch {
            val result = getDailyProgress()
            if (result is Result.Success) {
                _state.update {
                    it.copy(
                        dailyGoal = result.data.goal,
                        dailyDone = result.data.done,
                        streakDays = result.data.streakDays,
                    )
                }
            }
        }
    }
}
