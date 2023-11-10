package com.example.submissionintermediate.view.register

import androidx.lifecycle.ViewModel
import com.example.submissionintermediate.data.repository.UserRepository


class RegisterViewModel(private val repository: UserRepository):ViewModel() {
        fun register(name:String,email:String,password:String) = repository.register(name,email,password)
}
