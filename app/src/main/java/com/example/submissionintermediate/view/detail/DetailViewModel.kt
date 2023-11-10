package com.example.submissionintermediate.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionintermediate.data.ResultState
import com.example.submissionintermediate.data.repository.StoryRepository
import com.example.submissionintermediate.data.response.ResponseDetailStory

class DetailViewModel (val repository: StoryRepository):ViewModel() {
    fun getDetailStory(id:String):LiveData<ResultState<ResponseDetailStory>> {
     return  repository.getDetailStories(id)
    }
}