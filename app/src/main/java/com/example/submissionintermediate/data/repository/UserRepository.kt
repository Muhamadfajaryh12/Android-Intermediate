package com.example.submissionintermediate.data.repository

import androidx.lifecycle.liveData
import com.example.submissionintermediate.data.ResultState
import com.example.submissionintermediate.data.preference.UserModel
import com.example.submissionintermediate.data.preference.UserPreference
import com.example.submissionintermediate.data.response.ResponseError
import com.example.submissionintermediate.data.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
){
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun  register(name:String,email:String,password:String)= liveData{
        emit(ResultState.Loading)
        try{
            val responseSuccess = apiService.register(name,email,password)
            emit(ResultState.Success(responseSuccess.message.toString()))
        }catch(e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val responseError= Gson().fromJson(errorBody, ResponseError::class.java)
            emit(ResultState.Error(responseError.message.toString()))
        }
    }
    fun login(email:String,password: String) = liveData {
        emit(ResultState.Loading)
        try{
            val response = apiService.login(email,password)
            emit(ResultState.Success(response))
        }catch(e:HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val responseError= Gson().fromJson(errorBody, ResponseError::class.java)
            emit(ResultState.Error(responseError.message.toString()))
        }
    }


    suspend fun logout() {
        userPreference.logout()
    }
    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService:ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService  )
            }.also { instance = it }
        fun refreshRepository() {
            instance = null
        }
    }
}