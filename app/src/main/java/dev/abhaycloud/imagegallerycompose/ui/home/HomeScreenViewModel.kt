package dev.abhaycloud.imagegallerycompose.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abhaycloud.imagegallerycompose.repository.ImageRepository
import dev.abhaycloud.imagegallerycompose.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val imageRepository: ImageRepository): ViewModel() {

    var imageList = mutableStateOf(ImageListStateHolder())

    init {
        getImageList()
        imageList.value = ImageListStateHolder(isLoading = true)
    }

    fun getImageList(){
        viewModelScope.launch(Dispatchers.IO){
            val result = imageRepository.getImageList()
            when(result){
                is Resource.Success -> {
                    Log.d("myapp", result.data?.size.toString())
                    imageList.value = ImageListStateHolder(data = result.data)
                }

                is Resource.Error -> {
                    imageList.value = ImageListStateHolder(error = result.message.toString())
                }
                else -> {}
            }

        }
    }

}