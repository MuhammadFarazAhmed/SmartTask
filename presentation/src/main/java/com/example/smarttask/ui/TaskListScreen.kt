package com.example.smarttask.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.Task
import com.example.smarttask.common.LoaderFullScreen
import com.example.smarttask.ui.theme.SmartTaskTheme
import com.example.smarttask.vm.TasksViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListScreen(modifier: Modifier = Modifier, vm: TasksViewModel) {
    val state by vm.uiState.collectAsState()
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            state.loading -> LoaderFullScreen()
            state.emptyView -> {}
            state.tasks.isNotEmpty() -> {
                LazyColumn {
                    items(state.tasks.size) { index ->
                        val task = state.tasks[index]
                        Task(task = task)
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Task(modifier: Modifier = Modifier, task: Task) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {

        Column(modifier = modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
            Text(
                text = task.title ?: "Task Title",
                overflow = TextOverflow.Ellipsis,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.typography.titleMedium.color,
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                maxLines = 2,
                textAlign = TextAlign.Start,
                modifier = modifier.padding(bottom = 6.dp, end = 8.dp)
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.tertiary
            )

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    lineHeight = 20.sp,
                    text = multipleTextStyle(
                        suffix = "Due Date",
                        value = task.dueDate?.convertDateFormat() ?: "N/A"
                    ),
                    textAlign = TextAlign.Start
                )
                Text(
                    lineHeight = 20.sp,
                    text = multipleTextStyle(
                        suffix = "Days Left",
                        value = countDaysLeft(task.targetDate).toString()
                    ),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
private fun multipleTextStyle(suffix: String, value: String) = buildAnnotatedString {
    withStyle(
        style = SpanStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = MaterialTheme.typography.titleSmall.fontSize
        )
    )
    {
        append(suffix)
    }
    append("\n")
    withStyle(
        style = SpanStyle(
            color = MaterialTheme.typography.titleMedium.color,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
        )
    ) {
        append(value)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun countDaysLeft(targetDate: String?): Long? =
    targetDate?.let {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val targetLocalDate = LocalDate.parse(it, formatter)
        ChronoUnit.DAYS.between(targetLocalDate, LocalDate.now())
    }

@RequiresApi(Build.VERSION_CODES.O)
fun String.convertDateFormat(
    inputPattern: String = "yyyy-MM-dd",
    outputPattern: String = "MMM dd yyyy"
): String {
    val inputFormatter = DateTimeFormatter.ofPattern(inputPattern, Locale.getDefault())
    val outputFormatter = DateTimeFormatter.ofPattern(outputPattern, Locale.getDefault())
    val date = LocalDate.parse(
        this,
        inputFormatter
    )
    return date.format(outputFormatter)
}


@Preview
@Composable
fun TaskPreview(modifier: Modifier = Modifier) {
    SmartTaskTheme {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {

            Column(modifier = modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
                Text(
                    text = "Task Title",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.typography.titleMedium.color,
                    textAlign = TextAlign.Start,
                    modifier = modifier.padding(bottom = 6.dp)
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary
                )

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        lineHeight = 20.sp,
                        text = multipleTextStyle("Due Date", "2024-08-24"),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        lineHeight = 20.sp,
                        text = multipleTextStyle("Days Left", "12"),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskListScreenPreview(modifier: Modifier = Modifier) {
    SmartTaskTheme {
        Surface(
            modifier = modifier.fillMaxSize()
        ) {
            LazyColumn {
                items(10) {
                    TaskPreview()
                }
            }
        }
    }
}
