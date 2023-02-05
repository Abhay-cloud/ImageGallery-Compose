package dev.abhaycloud.imagegallerycompose.model

data class ImageList(
    val `data`: List<SingleImage>,
    val success: Boolean
)