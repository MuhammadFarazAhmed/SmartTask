package com.example.data.model.tasks.datasource

import com.example.data.model.common.makeApiCall
import com.example.data.model.model.TaskDto
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.http.path

class RemoteTasksDataSource(
    private val httpClient: HttpClient,
) : TasksDataSource {

    override suspend fun getTasks(): TaskDto = makeApiCall(httpClient)

}