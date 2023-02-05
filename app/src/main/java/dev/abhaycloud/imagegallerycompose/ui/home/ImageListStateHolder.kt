package dev.abhaycloud.imagegallerycompose.ui.home

import dev.abhaycloud.imagegallerycompose.model.SingleImage

data class ImageListStateHolder (
    val isLoading: Boolean = false,
    val data: List<SingleImage>? = null,
    var error: String = ""
)