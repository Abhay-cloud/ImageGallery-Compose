package dev.abhaycloud.imagegallerycompose.model

data class Error(
    val code: String,
    val errno: Int,
    val path: String,
    val syscall: String
)