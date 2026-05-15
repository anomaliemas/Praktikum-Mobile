package com.example.humasdirectorycompose.proker.presentation

import com.example.humasdirectorycompose.proker.data.Proker
import com.example.humasdirectorycompose.proker.data.ProkerData
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun HumasNavigation() {
    val navController = rememberNavController()
    val factory = ProkerViewModelFactory("HMTI Directory 2026")
    val viewModel: ProkerViewModel = viewModel(factory = factory)

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToDetail = { name ->
                    viewModel.logDetailClick(name)
                    navController.navigate("detail/$name")
                }
            )
        }
        composable(
            route = "detail/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val prokerName = backStackEntry.arguments?.getString("name")
            val prokerData = ProkerData.listData.find { it.name == prokerName }
            if (prokerData != null) {
                DetailScreen(
                    proker = prokerData,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}