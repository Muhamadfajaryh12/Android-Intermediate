package com.example.submissionintermediate.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.submissionintermediate.data.ResultState
import com.example.submissionintermediate.databinding.RegisterActivityBinding
import com.example.submissionintermediate.view.ViewModelFactory
import com.example.submissionintermediate.view.login.LoginActivity

class RegisterActivity: AppCompatActivity() {
    private lateinit var binding : RegisterActivityBinding

    private val viewModel by viewModels<RegisterViewModel>{
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction () {
        binding.login.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }
        binding.button.setOnClickListener {
            registerClicked()
        }
    }

    fun registerClicked(){
            val name = binding.textUsername.text.toString()
            val email = binding.editTextEmailAddress.text.toString()
            val password = binding.editTextPassword.text.toString()
            viewModel.register(name,email,password).observe(this){result->
                if(result !=null){
                    when(result){
                        is ResultState.Loading ->{
                            showLoading(true)
                        }
                        is ResultState.Success->{
                                showLoading(false)
                            alertShow("Success",result.data.toString())

                        }
                        is ResultState.Error->{
                            showLoading(false)
                            alertShow("Failed",result.error)
                        }
                    }
                }
            }
        }

    private fun playAnimation(){
        val username = ObjectAnimator.ofFloat(binding.textUsername,View.ALPHA,1f).setDuration(100)
        val email = ObjectAnimator.ofFloat(binding.editTextEmailAddress,View.ALPHA,1f).setDuration(100)
        val password = ObjectAnimator.ofFloat(binding.editTextPassword, View.ALPHA,1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.titleRegister,View.ALPHA,1f).setDuration(100)
        val relativeLayout = ObjectAnimator.ofFloat(binding.layoutText,View.ALPHA,1f).setDuration(100)
        val btn = ObjectAnimator.ofFloat(binding.button,View.ALPHA,1f).setDuration(100)
        val image = ObjectAnimator.ofFloat(binding.ivRegister,View.ALPHA,1f).setDuration(100)
        AnimatorSet().apply{
            playSequentially(
                username,
                email,
                password,
                title,
                relativeLayout,
                btn,
                image
            )
            startDelay=100
        }.start()
    }

    private fun alertShow(title :String,message:String){
        AlertDialog.Builder(this).apply{
            setTitle(title)
            setMessage(message)
            setPositiveButton("close"){ dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}