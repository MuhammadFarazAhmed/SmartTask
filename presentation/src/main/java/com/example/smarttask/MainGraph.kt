package com.example.smarttask

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarttask.ui.TaskListScreen
import com.example.smarttask.vm.TasksViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object TaskListRoute


@Composable
fun MainGraph(
    padding: PaddingValues,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = TaskListRoute
    ) {
        composable<TaskListRoute> {
            TaskListScreen(vm = koinViewModel<TasksViewModel>())
        }
    }
}
