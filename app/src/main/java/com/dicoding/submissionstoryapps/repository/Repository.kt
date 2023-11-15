package com.dicoding.submissionstoryapps.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.submissionstoryapps.Response.AddStoryResponse
import com.dicoding.submissionstoryapps.Response.ListStoryItem
import com.dicoding.submissionstoryapps.Response.LoginResponse
import com.dicoding.submissionstoryapps.Response.RegisterResponse
import com.dicoding.submissionstoryapps.Response.StoryResponse
import com.dicoding.submissionstoryapps.database.StoryDatabase
import com.dicoding.submissionstoryapps.database.StoryRemoteMediator
import com.dicoding.submissionstoryapps.pref.UserModel
import com.dicoding.submissionstoryapps.pref.UserPreference
import com.dicoding.submissionstoryapps.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import androidx.paging.*


class Repository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
    private val storyDatabase: StoryDatabase
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun getStory(token: String): StoryResponse{
        return apiService.getStories(token)
    }

    suspend fun uploadImage(token: String,imageFile: MultipartBody.Part, description: RequestBody): AddStoryResponse {
        return apiService.uploadImage(token,imageFile, description)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun login(email: String, password: String): LoginResponse{
        return apiService.login(email, password)
    }

    suspend fun register(name: String, email: String, password: String): RegisterResponse{
        return apiService.register(name, email, password)
    }

    fun getStory(): LiveData<PagingData<ListStoryItem>>{
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase,apiService, this),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
            storyDatabase: StoryDatabase
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(userPreference, apiService, storyDatabase)
            }.also { instance = it }
    }
}