package com.example.viikko1.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko1.domain.Task
import com.example.viikko1.viewmodel.TaskViewModel
import androidx.compose.runtime.getValue

@Composable
fun CalendarScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    val grouped = tasks.groupBy { it.dueDate }

    var showAddDialog by remember { mutableStateOf(false) }

    if (showAddDialog) {
        AddTaskDialog(
            onDismiss = { showAddDialog = false },
            onSave = {
                viewModel.addTask(it)
                showAddDialog = false
            }
        )
    }

    Column(Modifier.padding(16.dp)) {
        Button(onClick = { showAddDialog = true }) {
            Text("Add task")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            grouped.entries.forEach { entry ->
                val date = entry.key
                val tasksForDate = entry.value

                item {
                    Text(date, style = MaterialTheme.typography.titleMedium)
                }
                items(tasksForDate) { task: Task ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { /* voit lisätä editointidialogin tähän */ }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { viewModel.toggleDone(task.id) }
                        )
                        Text(task.title, Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

