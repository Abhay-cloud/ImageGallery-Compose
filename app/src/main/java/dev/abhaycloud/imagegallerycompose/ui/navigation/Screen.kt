package dev.abhaycloud.imagegallerycompose.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen(route = "home")
    object Create: Screen(route = "create")
}
