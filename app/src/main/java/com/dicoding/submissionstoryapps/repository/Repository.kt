package com.dicoding.submissionstoryapps.repository

import com.dicoding.submissionstoryapps.Response.AddStoryResponse
import com.dicoding.submissionstoryapps.Response.LoginResponse
import com.dicoding.submissionstoryapps.Response.RegisterResponse
import com.dicoding.submissionstoryapps.Response.StoryResponse
import com.dicoding.submissionstoryapps.pref.UserModel
import com.dicoding.submissionstoryapps.pref.UserPreference
import com.dicoding.submissionstoryapps.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody


class Repository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
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

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(userPreference, apiService)
            }.also { instance = it }
    }
}