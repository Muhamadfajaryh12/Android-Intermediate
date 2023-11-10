package com.example.submissionintermediate.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionintermediate.data.repository.StoryRepository
import com.example.submissionintermediate.data.repository.UserRepository
import com.example.submissionintermediate.di.Injection
import com.example.submissionintermediate.view.detail.DetailViewModel
import com.example.submissionintermediate.view.login.LoginViewModel
import com.example.submissionintermediate.view.main.MainViewModel
import com.example.submissionintermediate.view.maps.MapViewModel
import com.example.submissionintermediate.view.register.RegisterViewModel
import com.example.submissionintermediate.view.upload.UploadViewModel

class ViewModelFactory(
    private val repositoryUser: UserRepository,
    private val repositoryStory:StoryRepository
    ) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repositoryUser,repositoryStory) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repositoryUser) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java)->{
                RegisterViewModel(repositoryUser)as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java)->{
                DetailViewModel(repositoryStory) as T
            }
            modelClass.isAssignableFrom(UploadViewModel::class.java)->{
                UploadViewModel(repositoryStory) as T
            }
            modelClass.isAssignableFrom(MapViewModel::class.java)->{
                MapViewModel(repositoryStory) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    val userRepository = Injection.provideUserRepository(context)
                    val anotherRepository = Injection.provideStoryRepository(context)
                    INSTANCE = ViewModelFactory(userRepository, anotherRepository)

                }
            }
            return INSTANCE as ViewModelFactory
        }
        fun resetInstance() {
            INSTANCE = null
            Injection.resetInstance()
        }
    }
}