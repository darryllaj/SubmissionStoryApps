package com.dicoding.submissionstoryapps.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionstoryapps.R
import com.dicoding.submissionstoryapps.Response.ListStoryItem
import com.dicoding.submissionstoryapps.activity.DetailActivity
import com.dicoding.submissionstoryapps.activity.LoginActivity
import com.dicoding.submissionstoryapps.databinding.ActivityStoryBinding
import com.dicoding.submissionstoryapps.viewModels.ViewModelFactory
import kotlinx.coroutines.launch

class StoryActivity : AppCompatActivity() {
    private val viewModel by viewModels<StoryViewModels> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.listStory.observe(this){
            setStory(it)
        }
        lifecycleScope.launch {
            viewModel.getAllStory()
        }
        viewModel.isLoading.observe(this){
            showLoading(it)
        }
        binding.floatAdd.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    viewModel.logout()
                    AlertDialog.Builder(this).apply {
                        setTitle("Selamat Tinggal!")
                        setMessage("Anda berhasil Logout. Sampai Jumpa Lagi :D.")
                        setPositiveButton("Ok") { _, _ ->
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                    true
                }

                else -> false
            }
        }
    }
    private fun setStory(storyItems: List<ListStoryItem>){
        val adapter = StoryAdapter(storyItems)
        binding.rvListStory.setHasFixedSize(true)
        binding.rvListStory.layoutManager = LinearLayoutManager(this)
        binding.rvListStory.adapter = adapter
        adapter.setOnItemCallback(object : StoryAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ListStoryItem) {
                Intent(this@StoryActivity, DetailActivity::class.java).also {
                    intent ->
                    intent.putExtra(DetailActivity.NAME, data.name)
                    intent.putExtra(DetailActivity.IMG, data.photoUrl)
                    intent.putExtra(DetailActivity.DATE, data.createdAt)
                    intent.putExtra(DetailActivity.DESC, data.description)
                    startActivity(intent)
                }
            }

        })
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}