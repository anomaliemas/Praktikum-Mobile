package com.example.catapp.cat.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.ui.platform.LocalContext

@Composable
fun CatNavigation() {
    val context = LocalContext.current
    val database = com.example.catapp.core.database.CatDatabase.getDatabase(context)
    val apiService = com.example.catapp.core.network.ApiClient.apiService
    val repository = com.example.catapp.cat.data.CatRepository(apiService, database.catDao())
    val prefsHelper = com.example.catapp.core.SharedPrefsHelper(context)

    val factory = CatViewModelFactory(repository, prefsHelper, "Cat App")
    val viewModel: CatViewModel = viewModel(factory = factory)

    val navController = rememberNavController()

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
            arguments = listOf(navArgument(name = "name") { type = NavType.StringType })
        ) { backStackEntry ->
            val catId = backStackEntry.arguments?.getString("name")

            if (catId != null) {
                DetailScreen(
                    catId = catId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}