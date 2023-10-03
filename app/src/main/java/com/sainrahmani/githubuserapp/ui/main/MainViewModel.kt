package com.sainrahmani.githubuserapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sainrahmani.githubuserapp.api.ApiConfig
import com.sainrahmani.githubuserapp.data.GithubResponse
import com.sainrahmani.githubuserapp.data.ItemsItem
import com.sainrahmani.githubuserapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        val TAG = "MainActivity"
    }

    private val _user = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    init {
        findUser("sainr")
    }

    internal fun findUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFindUsers(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _user.value = response.body()?.items
                    } else {
                        _isLoading.value = false
                        _snackbarText.value = Event("onFailure: ${response.message()}")
                    }

                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event("onFailure: ${t.message}")
            }

        })

    }

}