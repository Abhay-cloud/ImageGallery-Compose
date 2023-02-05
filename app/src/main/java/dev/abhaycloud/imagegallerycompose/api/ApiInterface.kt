package dev.abhaycloud.imagegallerycompose.api

import dev.abhaycloud.imagegallerycompose.model.CreateModel
import dev.abhaycloud.imagegallerycompose.model.DalleModel
import dev.abhaycloud.imagegallerycompose.model.ImageList
import dev.abhaycloud.imagegallerycompose.util.Constants
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET(Constants.GET_IMAGES)
    suspend fun getImageList(): Response<ImageList>

    @FormUrlEncoded
    @POST(Constants.REQUEST_IMAGE)
    suspend fun createImage(@Field(Constants.prompt) prompt: String): Response<DalleModel>

    @FormUrlEncoded
    @POST(Constants.POST_IMAGE)
    suspend fun shareImage(@Field(Constants.name) name: String,
                           @Field(Constants.prompt) prompt: String,
                           @Field(Constants.photo) photo:String): Response<CreateModel>

}