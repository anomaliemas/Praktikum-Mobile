package com.example.humasdirectorycompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun HumasNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onNavigateToDetail = { name ->
                navController.navigate("detail/$name")
            })
        }
        composable(
            route = "detail/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val prokerName = backStackEntry.arguments?.getString("name")
            val prokerData = ProkerData.listData.find { it.name == prokerName }
            if (prokerData != null) {
                DetailScreen(proker = prokerData, onBack = { navController.popBackStack() })
            }
        }
    }
}