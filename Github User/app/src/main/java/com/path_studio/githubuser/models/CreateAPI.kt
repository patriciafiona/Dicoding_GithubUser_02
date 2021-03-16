package com.path_studio.githubuser.models

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


object CreateAPI {

    private const val baseURL = "https://api.github.com/"

    fun create(): GitHubService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
        return retrofit.create(GitHubService::class.java)
    }

}