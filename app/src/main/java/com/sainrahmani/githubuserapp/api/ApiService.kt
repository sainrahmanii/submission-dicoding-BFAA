package com.sainrahmani.githubuserapp.api

import com.sainrahmani.githubuserapp.data.GithubResponse
import com.sainrahmani.githubuserapp.data.ItemsItem
import com.sainrahmani.githubuserapp.data.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/search/users")
    fun getFindUsers(@Query("q") username: String?): Call<GithubResponse>

    @GET("/users/{username}")
    fun getDetailUsers(@Path("username") username: String?): Call<UserDetailResponse>

    @GET("/users/{username}/followers")
    fun getFollowers(@Path("username") username: String?): Call<List<ItemsItem>>

    @GET("/users/{username}/following")
    fun getFollowing(@Path("username") username: String?): Call<List<ItemsItem>>
}