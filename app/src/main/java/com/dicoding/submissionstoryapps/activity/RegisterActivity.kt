package com.dicoding.submissionstoryapps.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.submissionstoryapps.databinding.ActivityRegisterBinding
import com.dicoding.submissionstoryapps.viewModels.RegisterViewModels
import com.dicoding.submissionstoryapps.viewModels.ViewModelFactory


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModels> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginPagebtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        playAnimate()
        saveRegis()
    }
    private fun saveRegis(){
        binding.registerbtn.setOnClickListener {
            val name = binding.edName.text.toString().trim()
            val email = binding.edEmail.text.toString().trim()
            val password = binding.edPassword.text.toString().trim()
            viewModel.isLoading.observe(this){
                isShowLoading(it)
            }
            viewModel.register(name,email,password)
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("Anda berhasil register. Sudah tidak sabar untuk bercerita yaa?? Login dulu yaa...")
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
        }
    }
    private fun isShowLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun playAnimate(){
        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(500)
        val regInformation = ObjectAnimator.ofFloat(binding.tvRegInfo, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(1000)
        val nameEditText = ObjectAnimator.ofFloat(binding.NameTextInput, View.ALPHA,1f).setDuration(1000)
        val email = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(1000)
        val emailEditText = ObjectAnimator.ofFloat(binding.EmailTextInput, View.ALPHA,1f).setDuration(1000)
        val password = ObjectAnimator.ofFloat(binding.tvPassword,View.ALPHA, 1f).setDuration(1000)
        val passwordEditText = ObjectAnimator.ofFloat(binding.PasswordTextInput, View.ALPHA, 1f).setDuration(1000)
        val infoRegister = ObjectAnimator.ofFloat(binding.haveaccount, View.ALPHA, 1f).setDuration(1000)
        val btnRegister = ObjectAnimator.ofFloat(binding.registerbtn, View.ALPHA, 1f).setDuration(1000)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginPagebtn, View.ALPHA, 1f).setDuration(1000)

        AnimatorSet().apply {
            playSequentially(
                name,
                nameEditText,
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
            play(title).before(register)
            play(regInformation).after(register)
        }.start()

        ObjectAnimator.ofFloat(binding.reglogoStoru, View.TRANSLATION_X, -90f, 90f).apply {
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }
}