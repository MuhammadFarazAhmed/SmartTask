package com.example.smarttask.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Task
import com.example.domain.usecase.GetTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel(
    private val getTaskUseCase: GetTaskUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchMediaState> = MutableStateFlow(SearchMediaState())
    val uiState = _uiState.asStateFlow()

    init {
        getTasks()
    }

    private fun getTasks() {
        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            getTaskUseCase().collectLatest { data ->
                _uiState.update {
                    it.copy(
                        loading = false,
                        tasks = data,
                        emptyView = data.isEmpty()
                    )
                }
            }
        }
    }
}

data class SearchMediaState(
    val emptyView: Boolean = true,
    val loading: Boolean = false,
    val tasks: List<Task> = emptyList(),
)