package com.example.data.model.model


import com.example.domain.model.Task
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    @SerializedName("tasks")
    val tasks: List<Task> = emptyList()
) {
    @Serializable
    data class Task(
        @SerializedName("id")
        val id: String,
        @SerializedName("TargetDate")
        val targetDate: String?,
        @SerializedName("DueDate")
        val dueDate: String?,
        @SerializedName("Title")
        val title: String?,
        @SerializedName("Description")
        val description: String?,
        @SerializedName("Priority")
        val priority: Int?
    )
}

fun TaskDto.Task.toDomain() = Task(
    id = id,
    targetDate = targetDate,
    dueDate = dueDate,
    title = title,
    description = description,
    priority = priority

)
