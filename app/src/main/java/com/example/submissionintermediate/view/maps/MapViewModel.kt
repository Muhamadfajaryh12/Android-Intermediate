package com.example.submissionintermediate.view.maps

import androidx.lifecycle.ViewModel
import com.example.submissionintermediate.data.repository.StoryRepository

class MapViewModel (private val repository: StoryRepository) :ViewModel(){
    fun getStoryWithLocation() = repository.getListStoryWithLocation()
}