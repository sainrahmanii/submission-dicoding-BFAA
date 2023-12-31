package com.sainrahmani.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sainrahmani.githubuserapp.api.ApiConfig
import com.sainrahmani.githubuserapp.data.UserDetailResponse
import com.sainrahmani.githubuserapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMainViewModel : ViewModel() {
    private val _userDetail = MutableLiveData<UserDetailResponse>()
    val detail: LiveData<UserDetailResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbar = MutableLiveData<Event<String>>()
    val snackbar: LiveData<Event<String>> = _snackbar

    internal fun findDetail(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUsers(username)
        client.enqueue(object : Callback<UserDetailResponse>{
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _userDetail.value = response.body()
                    }else{
                        _isLoading.value = false
                        _snackbar.value = Event("onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                _snackbar.value = Event("onFailure: ${t.message}")
            }

        })
    }

}