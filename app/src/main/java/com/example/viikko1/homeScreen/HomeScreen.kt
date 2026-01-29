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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikko1.domain.Task
import com.example.viikko1.viewmodel.TaskViewModel
import java.time.LocalDate




@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = viewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    val today = LocalDate.now().toString()

    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDescription by remember { mutableStateOf("") }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    if (selectedTask != null) {
        DetailDialog(
            task = selectedTask!!,
            onDismiss = { selectedTask = null },
            onUpdate = { viewModel.updateTask(it) },
            onDelete = { viewModel.removeTask(selectedTask!!.id)
            selectedTask = null}

        )
    }



    Column(modifier.padding(16.dp)) {

        Text("Task list")


        TextField(
            value = newTaskTitle,
            onValueChange = { newTaskTitle = it },
            label = { Text("New task title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = newTaskDescription,
            onValueChange = { newTaskDescription = it },
            label = { Text("New task description") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (newTaskTitle.isNotBlank()) {
                    val newId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                    viewModel.addTask(
                        Task(
                            id = newId,
                            title = newTaskTitle,
                            description = newTaskDescription,
                            priority = 1,
                            dueDate = today,
                            done = false
                        )
                    )
                    newTaskTitle = ""
                    newTaskDescription = ""
                }
            }
        ) {
            Text("Add task")
        }




        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(tasks) { task ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = task.done,
                        onCheckedChange = { viewModel.toggleDone(task.id) }
                    )

                    Text(
                        text = "${task.title} (${task.dueDate})",
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTask = task }
                    )


                    Button(onClick = { viewModel.removeTask(task.id) }) {
                        Text("Delete")
                    }


                }
            }
        }
    }
}



@Composable
fun DetailDialog(
    task: Task,
    onDismiss: () -> Unit,
    onUpdate: (Task) -> Unit,
    onDelete: () -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Task") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                TextField(value = description, onValueChange = {description = it}, label = { Text("Description") })

            }
        },
        confirmButton = {
            Button(onClick = {
                onUpdate(task.copy(title = title, description = description))
                onDismiss()
            }) {
                Text("Save")
            }
        },
        dismissButton = {

            Row { Button(onClick = onDelete) {
                Text("Delete")
            }
                Spacer(Modifier.width(8.dp))
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    }

    )
}
