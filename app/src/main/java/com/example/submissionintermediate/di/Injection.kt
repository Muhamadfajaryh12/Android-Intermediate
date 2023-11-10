package com.example.submissionintermediate.di

import android.content.Context
import com.example.submissionintermediate.data.database.StoryDatabase
import com.example.submissionintermediate.data.repository.UserRepository
import com.example.submissionintermediate.data.preference.UserPreference
import com.example.submissionintermediate.data.preference.dataStore
import com.example.submissionintermediate.data.repository.StoryRepository
import com.example.submissionintermediate.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }

    fun provideStoryRepository(context:Context):StoryRepository{
        val pref= UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        val storyDatabase = StoryDatabase.getDatabase(context)
        return  StoryRepository.getInstance(pref,apiService,storyDatabase)
    }

    fun resetInstance() {
        UserRepository.refreshRepository()
        StoryRepository.refreshRepository()
    }
}