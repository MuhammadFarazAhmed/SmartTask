package com.example.data.model.tasks.repository

import android.util.Log
import com.example.data.model.model.toDomain
import com.example.data.model.tasks.datasource.TasksDataSource
import com.example.domain.model.Task
import com.example.domain.repositories.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TaskRepositoryImp(
    private val ioDispatcher: CoroutineDispatcher,
    private val remoteTasksDataSource: TasksDataSource,
    private val localTasksDataSource: TasksDataSource
) : TaskRepository {

    override fun getTasks(): Flow<List<Task>> =
        flow {
            emit(remoteTasksDataSource.getTasks().tasks.map { it.toDomain() })
        }.flowOn(ioDispatcher)
            .catch { e ->
                Log.d("TAG", "getTasks: $e")
                emit(emptyList())
            }

}
