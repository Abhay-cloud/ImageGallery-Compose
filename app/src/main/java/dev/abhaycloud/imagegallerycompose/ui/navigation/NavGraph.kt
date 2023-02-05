package dev.abhaycloud.imagegallerycompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.abhaycloud.imagegallerycompose.ui.create.CreateScreen
import dev.abhaycloud.imagegallerycompose.ui.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(route = Screen.Home.route){
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Create.route){
            CreateScreen(navController = navController)
        }
    }
}