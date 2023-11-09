package com.dicoding.submissionstoryapps.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionstoryapps.Response.LoginResponse

import com.dicoding.submissionstoryapps.pref.UserModel
import com.dicoding.submissionstoryapps.repository.Repository

import kotlinx.coroutines.launch

class LoginViewModels(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    suspend fun loginSession(email: String, password: String): LoginResponse{
        _isLoading.value = true
        try {
            val response = repository.login(email, password)
            _isLoading.value = false
            return response
        }catch (e: Exception){
            _isLoading.value = false
            throw e
        }
    }
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

}