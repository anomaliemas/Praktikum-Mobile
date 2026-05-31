package com.example.humasdirectorycompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun HumasNavigation(
    navController: NavHostController,
    viewModel: ProkerViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToDetail = { name ->
                    navController.navigate("detail/$name")
                }
            )
        }
        composable("detail/{prokerName}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("prokerName") ?: ""
            DetailScreen(
                prokerName = name,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}