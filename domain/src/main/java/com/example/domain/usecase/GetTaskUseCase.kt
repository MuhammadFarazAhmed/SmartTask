package com.example.domain.usecase

import com.example.domain.model.Task
import com.example.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTaskUseCase(private val taskRepository: TaskRepository) {

    operator fun invoke(): Flow<List<Task>> = taskRepository.getTasks()

}