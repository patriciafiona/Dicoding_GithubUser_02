package com.path_studio.githubuser.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    //Get User Data and Details
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<User>

    // Get Users public repositories
    @GET("users/{username}/repos?per_page=100")
    fun getUserPublicRepositories(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<List<Repository>>

    //Get User Starred Repositories
    @GET("users/{username}/starred?per_page=100")
    fun getUserStarredRepositories(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<List<Repository>>

    //Get My Repositories (All public, private, and colab repo)
    @GET("user/repos?per_page=100")
    fun getMyRepositories(@Header("Authorization") accessToken: String): Call<List<Repository>>

    //Get User Organization
    @GET("users/{username}/orgs")
    fun getUserOrganizations(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<List<Organization>>

    //Get Search User Result List
    @GET("search/users")
    fun getSearchByUsername(
            @Query("q") username: String?,
            @Header("Authorization") accessToken: String
    ): Call<Search>
}