package com.example.submissionintermediate.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.submissionintermediate.R
import com.example.submissionintermediate.view.main.MainActivity

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, Delayed)
        setupView()
    }

    private fun setupView(){
        supportActionBar?.hide()

    }
    companion object{
        private const val  Delayed:Long = 3000
    }
}
