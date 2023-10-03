package com.sainrahmani.githubuserapp.ui.detail.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sainrahmani.githubuserapp.api.ApiConfig
import com.sainrahmani.githubuserapp.data.ItemsItem
import com.sainrahmani.githubuserapp.ui.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentViewModel : ViewModel() {

    private val _userFollowers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _userFollowers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    internal fun findFollowers(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object  : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        Log.d("ZZ_FRAG", responseBody.toString())
                        _userFollowers.value = response.body()
                    } else {
                        _isLoading.value = false
                        Log.e(MainViewModel.TAG, "Detail onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainViewModel.TAG, "Detail onFailure: ${t.message}")
            }

        })
    }

    internal fun findFollowings(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object  : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        Log.d("ZZ_FRAG", responseBody.toString())
                        _userFollowers.value = response.body()
                    } else {
                        _isLoading.value = false
                        Log.e(MainViewModel.TAG, "Detail onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainViewModel.TAG, "Detail onFailure: ${t.message}")
            }

        })
    }
}