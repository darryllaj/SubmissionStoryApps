package com.dicoding.submissionstoryapps.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionstoryapps.Response.ErrorResponse
import com.dicoding.submissionstoryapps.Response.ListStoryItem
import com.dicoding.submissionstoryapps.repository.Repository
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException

class StoryViewModels(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> get() = _listStory

    private val _cekMessage = MutableLiveData<String>()
    val  cekMessage: LiveData<String> get() = _cekMessage

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }


    suspend fun getAllStory(){
        val tokenStory = repository.getSession().first().token
        val story = repository.getStory("Bearer $tokenStory")
        val message = story.message
        try {
            _isLoading.value = false
            //get success message
            _listStory.value = story.listStory
            _cekMessage.value = message!!
        } catch (e: HttpException) {
            //get error message
            _isLoading.value = false
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            errorMessage!!
        }
    }
}