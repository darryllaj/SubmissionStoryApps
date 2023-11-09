package com.dicoding.submissionstoryapps.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionstoryapps.Response.ErrorResponse
import com.dicoding.submissionstoryapps.repository.Repository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModels(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isErrorMessage = MutableLiveData<String>()
    val isErrorMessage: LiveData<String>get() = _isErrorMessage
    private val scope = viewModelScope

    fun register(name: String, email: String, password: String){
        scope.launch {
            _isLoading.value = true
            try {
                _isLoading.value = false
                val message = repository.register(name, email, password)
                val messageRegis = message.message
                _isErrorMessage.value = messageRegis!!
            } catch (e: HttpException) {
                //get error message
                _isLoading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                _isErrorMessage.value = errorMessage!!
            }
        }
    }
}