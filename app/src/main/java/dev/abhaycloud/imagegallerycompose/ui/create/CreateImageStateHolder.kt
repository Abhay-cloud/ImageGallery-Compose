package dev.abhaycloud.imagegallerycompose.ui.create

import dev.abhaycloud.imagegallerycompose.model.CreateModel
import dev.abhaycloud.imagegallerycompose.model.DalleModel
import dev.abhaycloud.imagegallerycompose.model.SingleImage

data class CreateImageStateHolder (
    val isLoading: Boolean = false,
    val data: DalleModel? = null,
    var error: String = ""
)

data class ShareImageStateHolder (
    val isLoading: Boolean = false,
    val data: CreateModel? = null,
    var error: String = ""
)