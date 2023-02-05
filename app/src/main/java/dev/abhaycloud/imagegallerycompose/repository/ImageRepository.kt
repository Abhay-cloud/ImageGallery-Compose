package dev.abhaycloud.imagegallerycompose.repository

import android.util.Log
import com.google.gson.Gson
import dev.abhaycloud.imagegallerycompose.api.ApiInterface
import dev.abhaycloud.imagegallerycompose.model.CreateModel
import dev.abhaycloud.imagegallerycompose.model.DalleModel
import dev.abhaycloud.imagegallerycompose.model.ErrorResponse
import dev.abhaycloud.imagegallerycompose.model.SingleImage
import dev.abhaycloud.imagegallerycompose.util.Resource

class ImageRepository(private val apiInterface: ApiInterface) {

    suspend fun getImageList(): Resource<List<SingleImage>>{
        val response = apiInterface.getImageList()

        if (response.isSuccessful && response.body() != null){
            return Resource.Success(response.body()!!.data)
        }

        if (response.errorBody() != null){
            return Resource.Error(response.errorBody().toString())
        }

        return Resource.Error("Something went wrong")
    }

    suspend fun createImage(prompt: String): Resource<DalleModel> {
        val response = apiInterface.createImage(prompt)

        if (response.isSuccessful && response.body() != null){
            return Resource.Success(response.body())
        }

        if (response.errorBody() != null){
            return Resource.Error(response.errorBody().toString())
        }

        return Resource.Error(response.errorBody().toString())
    }

    suspend fun shareImage(name: String, prompt: String, photo: String): Resource<CreateModel> {

        try {
            val response = apiInterface.shareImage(name, prompt, photo)
            Log.d("myapp", response.errorBody().toString())

            if (response.isSuccessful && response.body() != null){
                return Resource.Success(response.body())
            }

            if (response.errorBody() != null){
                val errorResponse = Gson().fromJson(response.errorBody()?.charStream(),ErrorResponse::class.java)
                Log.d("myapp", "${errorResponse}")
                return Resource.Error(response.errorBody().toString())
            }
        } catch (e: Exception){
            Log.d("myapp", e.message.toString())
        }



        return Resource.Error("Something went wrong")

    }



}