package com.example.smarttask

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
data object TaskListRoute


@Composable
fun MainGraph(
    padding: PaddingValues,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = TaskListRoute
    ) {
        composable<TaskListRoute> {

        }
    }
}
