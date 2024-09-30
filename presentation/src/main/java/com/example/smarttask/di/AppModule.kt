package com.example.smarttask.di

import android.util.Log
import com.example.data.model.tasks.datasource.LocalTasksDataSource
import com.example.data.model.tasks.datasource.RemoteTasksDataSource
import com.example.data.model.tasks.datasource.TasksDataSource
import com.example.data.model.tasks.repository.TaskRepositoryImp
import com.example.domain.repositories.TaskRepository
import com.example.domain.usecase.GetTaskUseCase
import com.example.smarttask.BuildConfig
import com.example.smarttask.vm.TasksViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun featureModules() = listOf(taskModule)

val AppModule = module {
    single { Dispatchers.IO }
    single { CoroutineScope(get()) }
}

val NetworkModule = module {
    single {
        HttpClient(Android) {

            defaultRequest {
                url {
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                    protocol = URLProtocol.HTTP
                    host = BuildConfig.API_URL
                }
            }

            install(Logging) { level = LogLevel.ALL }

            install(ContentNegotiation) {
                gson {
                    serializeNulls()
                }
            }

            HttpResponseValidator {
                validateResponse {
                    when (it.status.value) {
                        in 300..399 -> {}
                        in 400..499 -> {
                            Log.d("TAG", "${it.status.value}")
                        }

                        in 500..599 -> {}
                    }
                }
                handleResponseExceptionWithRequest { cause: Throwable, request: HttpRequest ->
                    Log.d("TAG", "$cause: $request")
                }
            }

            install(ResponseObserver) {
                onResponse { response ->
                    println("HTTP status: ${response.status.value}")
                }
            }

        }
    }
}

val taskModule = module {

    single<TasksDataSource>(named("LocalTasksDataSource")) { LocalTasksDataSource() }
    single<TasksDataSource>(named("RemoteTasksDataSource")) { RemoteTasksDataSource(get()) }

    single<TaskRepository> {
        TaskRepositoryImp(
            ioDispatcher = get(),
            remoteTasksDataSource = get(named("RemoteTasksDataSource")),
            localTasksDataSource = get(named("LocalTasksDataSource")),
        )
    }

    single { GetTaskUseCase(get()) }

    viewModel { TasksViewModel(get()) }
}
