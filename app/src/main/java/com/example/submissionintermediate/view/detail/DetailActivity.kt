package com.example.submissionintermediate.view.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.submissionintermediate.data.ResultState
import com.example.submissionintermediate.data.response.ResponseDetailStory
import com.example.submissionintermediate.databinding.DetailActivityBinding
import com.example.submissionintermediate.view.ViewModelFactory

class DetailActivity :AppCompatActivity() {
    private lateinit var binding: DetailActivityBinding
    private val viewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(this)
    }

    private var id : String? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding=DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
    }

    private fun setupViewModel(){
        id =intent.getStringExtra(EXTRA_ID)
        viewModel.getDetailStory(id.toString()).observe(this){
            result ->
            if(result != null){
                when(result){
                    is ResultState.Loading->{
                        showLoading(true)
                    }
                    is ResultState.Success->{
                        showLoading(false)
                        setupDataDetailStory(result.data)
                    }
                    is ResultState.Error->{
                        showLoading(false)
                        showToast(result.error)
                    }
                }
            }
            
        }
    }
    private fun setupDataDetailStory(story:ResponseDetailStory){
        binding.apply {
            Glide.with(ivImage.context).load(story.story?.photoUrl).into(binding.ivImage)
            tvTitle.text=story.story?.name
            tvDescription.text=story.story?.description
            tvLat.text= story.story?.lat.toString()
            tvLon.text=story.story?.lon.toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object{
        const val EXTRA_ID = ""
    }
}