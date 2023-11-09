package com.dicoding.submissionstoryapps.story

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionstoryapps.Response.AddStoryResponse
import com.dicoding.submissionstoryapps.reduceFileImage
import com.dicoding.submissionstoryapps.repository.Repository
import com.dicoding.submissionstoryapps.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class AddStoryViewModels(private val repository: Repository): ViewModel() {
    var _currentImageUri = MutableLiveData<Uri>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _errormsg = MutableLiveData<String>()
    val errormsg: LiveData<String> get() = _errormsg

    fun setUploadImage(context: Context, desc: String) {
        viewModelScope.launch {
            uploadImage(context, desc)
        }
    }


    suspend fun uploadImage(context: Context, desc: String){
        if (_currentImageUri.value != null || desc.isEmpty()){
            _currentImageUri.let { uri ->
                val imageFile = uriToFile(_currentImageUri.value!!, context).reduceFileImage()
                Log.d("Image File", "showImage: ${imageFile.path}")
                val description = desc
                _isLoading.value = true
                val requestBody = description.toRequestBody("text/plain".toMediaType())
                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData(
                    "photo",
                    imageFile.name,
                    requestImageFile)

                try {
                    val tokenStory = ("Bearer ${repository.getSession().first().token}")
                    val uploadFile = repository.uploadImage(tokenStory,multipartBody, requestBody)
                    _errormsg.value = uploadFile.message!!
                } catch (e: HttpException){
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, AddStoryResponse::class.java)
                    _errormsg.value= errorResponse.message!!
                    _isLoading.value = false
                }
            }
        } else{
            _errormsg.value = "Isi Dulu Deskripsi atau Foto Terlebih Dahulu"
        }
    }
}