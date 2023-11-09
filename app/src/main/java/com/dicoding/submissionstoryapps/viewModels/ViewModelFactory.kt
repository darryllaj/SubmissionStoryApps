package com.dicoding.submissionstoryapps.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissionstoryapps.main.MainViewModels
import com.dicoding.submissionstoryapps.injection.Injection
import com.dicoding.submissionstoryapps.repository.Repository
import com.dicoding.submissionstoryapps.story.AddStoryViewModels
import com.dicoding.submissionstoryapps.story.StoryViewModels

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModels::class.java) -> {
                MainViewModels(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModels::class.java) -> {
                RegisterViewModels(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModels::class.java) -> {
                LoginViewModels(repository) as T
            }modelClass.isAssignableFrom(StoryViewModels::class.java) -> {
                StoryViewModels(repository) as T
            }modelClass.isAssignableFrom(AddStoryViewModels::class.java) -> {
                AddStoryViewModels(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }

    }
}