package com.example.viikko1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.viikko1.Navigation.AppNavHost
import com.example.viikko1.Navigation.Routes
import com.example.viikko1.viewmodel.TaskViewModel
import com.example.viikko1.ui.theme.Viikko1Theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Viikko1Theme {
                val navController = rememberNavController()
                val viewModel: TaskViewModel = viewModel()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("ToDo App") },
                            actions = {
                                IconButton(onClick = {
                                    val current = navController.currentDestination?.route
                                    if (current == Routes.HOME)
                                        navController.navigate(Routes.CALENDAR)
                                    else
                                        navController.navigate(Routes.HOME)
                                }) {
                                    val current = navController.currentDestination?.route
                                    Icon(
                                        imageVector = if (current == Routes.HOME)
                                            Icons.Default.DateRange
                                        else
                                            Icons.Default.List,
                                        contentDescription = "Vaihda näkymää"
                                    )
                                }
                                IconButton(onClick = {
                                    navController.navigate(Routes.SETTINGS)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Asetukset"
                                    )
                                }

                            }
                        )
                    }
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

