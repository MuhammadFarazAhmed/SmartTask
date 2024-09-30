package com.example.data.model.tasks.datasource

import com.example.data.model.common.unsupported
import com.example.data.model.model.TaskDto

class LocalTasksDataSource : TasksDataSource {

    override suspend fun getTasks(): TaskDto {
        unsupported()
    }

}