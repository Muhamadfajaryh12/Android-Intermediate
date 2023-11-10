package com.example.submissionintermediate.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.submissionintermediate.view.login.LoginActivity
import com.example.submissionintermediate.view.ViewModelFactory
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionintermediate.R
import com.example.submissionintermediate.databinding.ActivityMainBinding
import com.example.submissionintermediate.view.maps.MapsActivity
import com.example.submissionintermediate.view.upload.UploadActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            else{
                setupRecycleView()
                setupViewModel()
                setupAction()
            }
        }
    }
    private fun setupAction(){
        binding.btnUpload.setOnClickListener{
            startActivity(Intent(this,UploadActivity::class.java))
        }
    }
    private fun setupRecycleView(){
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this,layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)
    }

    private fun setupViewModel(){
        val adapter = StoryAdapter(this@MainActivity)
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.getStory().observe(this){
            adapter.submitData(lifecycle,it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected (item: MenuItem) : Boolean{
        when(item.itemId){
            R.id.logout_menu->{
                viewModel.logout()
            }
            R.id.map_menu->{
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}