package com.path_studio.githubuser.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<User>

    @GET("users/{username}/repos?per_page=100")
    fun getUserPublicRepositories(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<List<Repository>>

    @GET("users/{username}/starred?per_page=100")
    fun getUserStarredRepositories(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<List<Repository>>

    @GET("user/repos?per_page=100")
    fun getMyRepositories(@Header("Authorization") accessToken: String): Call<List<Repository>>

    @GET("users/{username}/orgs")
    fun getUserOrganizations(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<List<Organization>>
}