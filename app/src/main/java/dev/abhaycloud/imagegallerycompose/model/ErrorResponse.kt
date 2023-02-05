package dev.abhaycloud.imagegallerycompose.model

data class ErrorResponse(
    val message: Message,
    val success: Boolean
)