package com.example.data.model.tasks.datasource

import com.example.data.model.model.TaskDto

interface TasksDataSource {

    suspend fun getTasks(): TaskDto

}