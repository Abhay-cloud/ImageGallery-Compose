package dev.abhaycloud.imagegallerycompose.ui.create

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
class CreateImageViewModel @Inject constructor(private val imageRepository: ImageRepository): ViewModel() {

    var imageResponse = mutableStateOf(CreateImageStateHolder())
    var shareImage = mutableStateOf(ShareImageStateHolder())

    fun createImage(prompt: String){
        imageResponse.value = CreateImageStateHolder(isLoading = true)
        viewModelScope.launch(Dispatchers.IO){
            val result = imageRepository.createImage(prompt)

            when(result){
                is Resource.Success -> {
                    imageResponse.value = CreateImageStateHolder(data = result.data)
                }

                is Resource.Error -> {
                    imageResponse.value = CreateImageStateHolder(error = result.message.toString())
                }

                else -> {

                }

            }

        }
    }

    fun shareImage(name: String, prompt: String, photo: String){
        shareImage.value = ShareImageStateHolder(isLoading = true)
        viewModelScope.launch(Dispatchers.IO){
            val result = imageRepository.shareImage(name, prompt, photo)
            when(result){
                is Resource.Success -> {
                    shareImage.value = ShareImageStateHolder(data = result.data)
                }

                is Resource.Error -> {
                    shareImage.value = ShareImageStateHolder(error = result.message.toString())
                }

                else -> {

                }

            }
        }
    }


}