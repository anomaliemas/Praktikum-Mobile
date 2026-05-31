package com.example.catapp.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.catapp.cat.presentation.CatViewModel
import com.example.catapp.cat.presentation.DetailScreen
import com.example.catapp.cat.presentation.HomeScreen

@Composable
fun AppNavigation(viewModel: CatViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToDetail = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val catId = backStackEntry.arguments?.getString("id") ?: ""
            DetailScreen(
                catId = catId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}