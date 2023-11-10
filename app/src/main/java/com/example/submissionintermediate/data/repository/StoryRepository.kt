package com.example.submissionintermediate.data.repository

import androidx.lifecycle.LiveData
import com.example.submissionintermediate.data.ResultState
import com.example.submissionintermediate.data.response.ResponseError
import com.google.gson.Gson
import retrofit2.HttpException
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.submissionintermediate.data.StoryRemoteMediator
import com.example.submissionintermediate.data.database.StoryDatabase
import com.example.submissionintermediate.data.preference.UserPreference
import com.example.submissionintermediate.data.response.ListStoryItem
import com.example.submissionintermediate.data.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class StoryRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
    private val storyDatabase: StoryDatabase
){
    fun getListStories(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
                    remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
        pagingSourceFactory = {storyDatabase.storyDao().getAllStories()
        }
        ).liveData
    }

    fun getListStoryWithLocation () = liveData {
        emit(ResultState.Loading)
        try{
            val response = apiService.getStoriesWithLocation()
            emit(ResultState.Success(response))
        }catch(e:HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val responseError = Gson().fromJson(errorBody,ResponseError::class.java)
            emit(ResultState.Error(responseError.message.toString()))
        }
    }
    fun getDetailStories (id:String) =liveData{
        emit(ResultState.Loading)
        try{
            val response = apiService.getDetailStory(id)
            emit(ResultState.Success(response))
        }catch(e:HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val responseError= Gson().fromJson(errorBody, ResponseError::class.java)
            emit(ResultState.Error(responseError.message.toString()))
        }
    }

    fun uploadStories (file: File, description: String,lat:Double,lon:Double): LiveData<ResultState<Any>> = liveData {
        emit(ResultState.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.uploadStory(multipartBody, requestBody,lat, lon)
            emit(ResultState.Success(successResponse.message.toString()))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ResponseError::class.java)
            emit(ResultState.Error(errorResponse.message.toString()))
        }
    }
    companion object{
        @Volatile
        private var instance : StoryRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
            storyDatabase: StoryDatabase
        ):StoryRepository =
            instance ?: synchronized(this){
             instance ?:   StoryRepository(userPreference, apiService, storyDatabase )
            }.also { instance = it }

            fun refreshRepository() {
                instance = null
            }
        }
    }
