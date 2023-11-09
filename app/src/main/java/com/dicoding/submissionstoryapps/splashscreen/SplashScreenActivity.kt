package com.dicoding.submissionstoryapps.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.submissionstoryapps.main.MainActivity
import com.dicoding.submissionstoryapps.R

class SplashScreenActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 3000 // Durasi splash screen dalam milidetik (3 detik)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            // Pindah ke MainActivity setelah splashTimeOut
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Tutup activity splash agar tidak bisa diakses lagi
        }, splashTimeOut)
    }
}