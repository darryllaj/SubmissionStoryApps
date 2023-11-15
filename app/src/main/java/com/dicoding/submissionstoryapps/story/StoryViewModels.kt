package com.dicoding.submissionstoryapps.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.submissionstoryapps.Response.ListStoryItem
import com.dicoding.submissionstoryapps.repository.Repository
import kotlinx.coroutines.launch


class StoryViewModels(private val repository: Repository): ViewModel() {
    val listStory: LiveData<PagingData<ListStoryItem>> = repository.getStory().cachedIn(viewModelScope)

    private val _cekMessage = MutableLiveData<String>()
    val  cekMessage: LiveData<String> get() = _cekMessage

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }


//    suspend fun getAllStory(){
//        val tokenStory = repository.getSession().first().token
//        val story = repository.getStory("Bearer $tokenStory")
//        val message = story.message
//        try {
//            _isLoading.value = false
//            //get success message
//            _listStory.value = story.listStory
//            _cekMessage.value = message!!
//        } catch (e: HttpException) {
//            //get error message
//            _isLoading.value = false
//            val jsonInString = e.response()?.errorBody()?.string()
//            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
//            val errorMessage = errorBody.message
//            errorMessage!!
//        }
//    }
}