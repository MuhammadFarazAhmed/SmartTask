package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    val targetDate: String? = "",
    val dueDate: String? = "",
    val title: String? = "",
    val description: String? = "",
    val priority: Int? = 0
)