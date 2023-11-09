package com.dicoding.submissionstoryapps.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.dicoding.submissionstoryapps.databinding.ActivityLoginBinding
import com.dicoding.submissionstoryapps.pref.UserModel
import com.dicoding.submissionstoryapps.story.StoryActivity
import com.dicoding.submissionstoryapps.viewModels.LoginViewModels
import com.dicoding.submissionstoryapps.viewModels.ViewModelFactory
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModels> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimate()
        binding.registerbtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        login()
        viewModel.isLoading.observe(this){
            isShowLoading(it)
        }
    }


    private fun login(){
        binding.loginbtn.setOnClickListener {
            val email = binding.edEmail.text.toString().trim()
            val password = binding.edPassword.text.toString().trim()
            lifecycleScope.launch {
                    val result = viewModel.loginSession(email, password)
                    val message = result.message
                    if (message != "error"){
                        when(result != null){
                            true -> {
                                AlertDialog.Builder(this@LoginActivity).apply {
                                    setTitle("Yeah!")
                                    setMessage("Anda berhasil Login. Silakan berceritaaa :D.")
                                    setPositiveButton("Lanjut") { _, _ ->
                                        val token = result.loginResult.token
                                        viewModel.saveSession(UserModel(email, token))

                                        val intent = Intent(context, StoryActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }
                            else -> {
                                AlertDialog.Builder(this@LoginActivity).apply {
                                    setTitle("Failed!")
                                    setMessage("Anda Gagal Login. Coba Lagi yaa :D.")
                                    setPositiveButton("Lanjut") { _, _ ->
                                        val intent = Intent(context, LoginActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }
                        }
                    }
            }
        }
    }
    private fun isShowLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
    private fun playAnimate(){
        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(500)
        val welcome = ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f).setDuration(500)
        val logInformation = ObjectAnimator.ofFloat(binding.tvLoginInformation, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(1000)
        val emailEditText = ObjectAnimator.ofFloat(binding.EmailTextInput, View.ALPHA,1f).setDuration(1000)
        val password = ObjectAnimator.ofFloat(binding.tvPassword,View.ALPHA, 1f).setDuration(1000)
        val passwordEditText = ObjectAnimator.ofFloat(binding.PasswordTextInput, View.ALPHA, 1f).setDuration(1000)
        val infoRegister = ObjectAnimator.ofFloat(binding.donthaveaccount, View.ALPHA, 1f).setDuration(1000)
        val btnRegister = ObjectAnimator.ofFloat(binding.registerbtn, View.ALPHA, 1f).setDuration(1000)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginbtn, View.ALPHA, 1f).setDuration(1000)

        AnimatorSet().apply {
            playSequentially(
                email,
                emailEditText,
                password,
                passwordEditText,
                infoRegister,
                btnRegister,
                btnLogin
            )
        }.start()
        AnimatorSet().apply {
            play(title).before(welcome)
            play(logInformation).after(welcome)
        }.start()


        ObjectAnimator.ofFloat(binding.logoStoru, View.TRANSLATION_X, -60f, 60f).apply {
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        ObjectAnimator.ofFloat(binding.ivFotogw, View.TRANSLATION_X, -250f, 500f).apply {
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.RESTART
        }.start()
    }
}
