package com.example.submissionintermediate.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submissionintermediate.data.repository.UserRepository
import com.example.submissionintermediate.data.preference.UserModel
import com.example.submissionintermediate.data.repository.StoryRepository
import com.example.submissionintermediate.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository,
                    private val storyRepository: StoryRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun getStory():LiveData<PagingData<ListStoryItem>> {
        return storyRepository.getListStories().cachedIn(viewModelScope)

    }
    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

}