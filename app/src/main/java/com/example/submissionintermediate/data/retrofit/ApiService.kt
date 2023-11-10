package com.example.submissionintermediate.data.retrofit

import com.example.submissionintermediate.data.response.ResponseDetailStory
import com.example.submissionintermediate.data.response.ResponseListStory
import com.example.submissionintermediate.data.response.ResponseLogin
import com.example.submissionintermediate.data.response.ResponseRegister
import com.example.submissionintermediate.data.response.ResponseUpload
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String
    ):ResponseRegister

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email:String,
        @Field("password") password: String
    ):ResponseLogin

    @GET("stories")
    suspend fun getStoriesList(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ):ResponseListStory

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location : Int = 1,
    ):ResponseListStory

    @GET("stories/{id}")
    suspend fun getDetailStory(@Path("id") id:String):ResponseDetailStory

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part file : MultipartBody.Part,
        @Part("description") description:RequestBody,
        @Part("lat") lat:Double? = null,
        @Part("lon") lon:Double? = null
    ):ResponseUpload
}