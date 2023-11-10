package com.example.submissionintermediate.view.upload

import androidx.lifecycle.ViewModel
import com.example.submissionintermediate.data.repository.StoryRepository
import java.io.File

class UploadViewModel(val repository: StoryRepository):ViewModel() {
    fun uploadStory(file: File, descrition:String,lat:Double,lon:Double)=repository.uploadStories(file,descrition,lat,lon)
}