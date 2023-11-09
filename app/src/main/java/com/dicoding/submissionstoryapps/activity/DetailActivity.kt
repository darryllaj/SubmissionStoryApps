package com.dicoding.submissionstoryapps.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.submissionstoryapps.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            binding.tvPublikasi.text = "Publikasi Pada: " + intent.getStringExtra(DATE)
            Glide.with(binding.root)
                .load(intent.getStringExtra(IMG))
                .into(binding.imageView3)
            binding.tvIsidesc.text = intent.getStringExtra(DESC)
            binding.tvJudul.text= intent.getStringExtra(NAME)
        }
    }
    companion object {
        const val NAME = "DETAIL_NAME"
        const val IMG = "DETAIL_IMG"
        const val DATE = "DETAIL_DATE"
        const val DESC = "DETAIL_DESC"
    }
}