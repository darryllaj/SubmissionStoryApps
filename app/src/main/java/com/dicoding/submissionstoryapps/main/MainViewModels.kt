package com.dicoding.submissionstoryapps.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.submissionstoryapps.pref.UserModel
import com.dicoding.submissionstoryapps.repository.Repository

class MainViewModels(private val repository: Repository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}