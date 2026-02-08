package com.example.viikko1.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.viikko1.viewmodel.TaskViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.viikko1.homeScreen.HomeScreen
import com.example.viikko1.homeScreen.CalendarScreen
import com.example.viikko1.homeScreen.SettingsScreen





@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: TaskViewModel,
    modifier: Modifier = Modifier

) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier

    ) {
        composable(Routes.HOME) {
            HomeScreen(viewModel = viewModel)

        }
        composable(Routes.CALENDAR) {
            CalendarScreen(viewModel = viewModel)
        }
        composable(Routes.SETTINGS) {
            SettingsScreen()
        }


    }
}
