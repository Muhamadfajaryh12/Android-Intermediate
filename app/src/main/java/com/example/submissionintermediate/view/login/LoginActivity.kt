package com.example.submissionintermediate.view.login
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.submissionintermediate.view.ViewModelFactory
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.example.submissionintermediate.data.ResultState
import com.example.submissionintermediate.data.preference.UserModel
import com.example.submissionintermediate.databinding.LoginActivityBinding
import com.example.submissionintermediate.view.main.MainActivity
import com.example.submissionintermediate.view.register.RegisterActivity

class LoginActivity:AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupAction() {
        binding.register.setOnClickListener{
            startActivity(Intent(this,RegisterActivity :: class.java))
        }
        binding.button.setOnClickListener {
            loginClicked()
        }
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

    private fun loginClicked () {
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextPassword.text.toString()
        viewModel.login(email, password).observe(this) { result ->
            if (result != null) {
                when(result) {
                    is ResultState.Loading -> {
                     showLoading(true)
                    }
                    is ResultState.Success -> {
                        showLoading(false)
                        val userModel = UserModel(email, result.data.loginResult?.token.toString(), true)
                        viewModel.saveSession(userModel)
                        AlertDialog.Builder(this).apply {
                            setTitle("Success")
                            setMessage(result.data.message.toString())
                            ViewModelFactory.resetInstance()
                            setPositiveButton("Close") { _, _ ->
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        alertShow("Failed",result.error)
                    }
                }
            }
        }
    }

    private fun playAnimation(){
        val title = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA,1f).setDuration(100)
        val emailLayout = ObjectAnimator.ofFloat(binding.editTextTextEmailAddress,View.ALPHA,1f).setDuration(100)
        val passwordLayout = ObjectAnimator.ofFloat(binding.editTextPassword, View.ALPHA,1f).setDuration(100)
        val btnLayout = ObjectAnimator.ofFloat(binding.button,View.ALPHA,1f).setDuration(100)
        val relativeLayout = ObjectAnimator.ofFloat(binding.rlId,View.ALPHA,1f).setDuration(100)
        val image = ObjectAnimator.ofFloat(binding.ivLogin, View.ALPHA,1f).setDuration(100)
        AnimatorSet().apply {
            playSequentially(
                title,
                emailLayout,
                passwordLayout,
                btnLayout,
                relativeLayout,
                image
            )
            startDelay = 100
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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
}