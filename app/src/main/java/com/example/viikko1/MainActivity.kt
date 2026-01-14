package com.example.viikko1

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.viikko1.domain.Task
import com.example.viikko1.domain.addTask
import com.example.viikko1.domain.filterByDone
import com.example.viikko1.domain.mockTasks
import com.example.viikko1.domain.sortByDueDate
import com.example.viikko1.ui.theme.Viikko1Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.viikko1.domain.toggleDone




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Viikko1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        tasks = mockTasks,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun HomeScreen(tasks: List<Task>, modifier: Modifier = Modifier) {

    var taskList by remember { mutableStateOf(tasks) }
    var newTaskTitle by remember { mutableStateOf("") }
    var activeFilter by remember { mutableStateOf<String?>(null) }
    var backUpList by remember { mutableStateOf(taskList) }



    Column(modifier = modifier.padding(16.dp)) {

        Text(text = "Task list")
        TextField(
            value = newTaskTitle,
            onValueChange = { newTaskTitle = it },
            label = { Text("New task title") }
)

        Spacer(Modifier.height(8.dp))


        Button(onClick = {
            if (newTaskTitle.isNotBlank()) {
                val newId = (backUpList.maxOfOrNull { it.id } ?: 0) + 1
                val newTask = Task(
                    id = newId,
                    title = newTaskTitle,
                    description = "",
                    priority = 1,
                    dueDate = "",
                    done = false
                )
                backUpList = addTask(backUpList, newTask)
                taskList = when (activeFilter) {
                    "done" -> filterByDone(backUpList, true)
                    "sort" -> sortByDueDate(backUpList)
                    else -> backUpList
                }
                newTaskTitle = ""

            }
        }) {
            Text("Add task")
        }

        Spacer(Modifier.height(8.dp))



        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            if (activeFilter != "sort") {
                taskList = sortByDueDate(backUpList)
                activeFilter = "sort"
            } else {
                taskList = backUpList
                activeFilter = null
            }
        }) {
            Text(if (activeFilter != "sort") "Sort by due date" else "Show original")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            if (activeFilter != "done") {
                taskList = filterByDone(backUpList, true)
                activeFilter = "done"
            } else {
                taskList = backUpList
                activeFilter = null
            }
        }) {
            Text(if (activeFilter != "done") "Show only done tasks" else "Show original")
        }


        Spacer(Modifier.height(16.dp))

        // LISTA
        LazyColumn {
            items(taskList) { task ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val status = if (!task.done) "❌" else "✔️"

                    Text(
                        text = "$status ${task.id}. ${task.title}. ${task.dueDate}",
                        modifier = Modifier.weight(1f)
                    )

                    Button(onClick = {
                        // 1. Päivitä oikea lista
                        backUpList = toggleDone(backUpList, task.id)

                        // 2. Päivitä näkyvä lista suodatuksen mukaan
                        taskList = when (activeFilter) {
                            "done" -> filterByDone(backUpList, true)
                            "sort" -> sortByDueDate(backUpList)
                            else -> backUpList
                        }
                    }) {
                        Text(if (task.done) "Done" else "Undone")
                    }
                }}

            }

            Spacer(Modifier.height(8.dp))
        }
}





@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Viikko1Theme {
        HomeScreen(tasks = mockTasks)
    }
}
