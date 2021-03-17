package com.path_studio.githubuser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.path_studio.githubuser.Utils
import com.path_studio.githubuser.adapters.ListPopularRepoAdapter
import com.path_studio.githubuser.databinding.ActivityDetailUserBinding
import com.path_studio.githubuser.fragments.ProfileFragment
import com.path_studio.githubuser.models.CreateAPI
import com.path_studio.githubuser.models.GitHubService
import com.path_studio.githubuser.models.Repository
import com.path_studio.githubuser.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUser: User
    private lateinit var listStarred: ArrayList<Repository>

    private lateinit var gitHubServiceitHubService: GitHubService

    companion object {
        const val EXTRA_USER = "extra_user"
        const val MY_USERNAME = "patriciafiona"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        showLoading(true)
        gitHubServiceitHubService = CreateAPI.create()

        //get User Details data from API & show all data into UI
        val data = intent.getParcelableExtra<User>(EXTRA_USER) as User
        getUserData(data.login.toString())
    }

    private fun getUserData(username: String){
        gitHubServiceitHubService.getUserDetail(username, ProfileFragment.ACCESS_TOKEN).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    detailUser = response.body() as User

                    //Show Data
                    showData(detailUser)

                    //get My Starred Repo
                    getMyStarredRepository(detailUser.login.toString())

                    //hide loading indicator
                    showLoading(false)
                }
            }

            override fun onFailure(call: Call<User>, error: Throwable) {
                Log.e("tag", "The Error is: ${error.message}")
            }
        })
    }

    private fun showData(user: User){
        binding.detailUserName.text = user.name.toString()
        binding.detailUserUsername.text = user.login.toString()
        binding.detailUserLocation.text = user.location.toString()
        binding.detailUserRepositories.text = (user.public_repos + user.owned_private_repos).toString()
        binding.detailUserCompany.text = user.company.toString()
        binding.detailUserEmail.text = user.email.toString()
        binding.detailUserLink.text = user.blog.toString()
        binding.detailUserFollowers.text = Utils.convertNumberFormat(user.followers)
        binding.detailUserFollowings.text = Utils.convertNumberFormat(user.following)

        Glide.with(this)
            .load(user.avatar_url)
            .apply(RequestOptions().override(800, 800))
            .into(binding.detailUserAvatar)

        if(MY_USERNAME.equals(user.login, true)){
            binding.btnFollow.visibility = View.GONE
        }else{
            binding.btnFollow.visibility = View.VISIBLE
        }

    }

    private fun getMyStarredRepository(username: String){
        gitHubServiceitHubService.getUserStarredRepositories(username, ProfileFragment.ACCESS_TOKEN).enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                    call: Call<List<Repository>>,
                    response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    listStarred = response.body() as ArrayList<Repository>

                    //show total repo
                    binding.detailUserStarred.text = listStarred.size.toString()

                    //show popular repo using horizontal recycle view
                    showStarredRepo()

                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("tag", "The Error is: ${t.message}")
            }

        })
    }

    private fun showStarredRepo(){
        val rvPopularRepo: RecyclerView = binding.rvUsersStarredRepo
        rvPopularRepo.setHasFixedSize(true)

        showRecyclerList(rvPopularRepo, listStarred)
    }

    private fun showRecyclerList(rvApp: RecyclerView, list: ArrayList<Repository>) {
        rvApp.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listAppAdapter = ListPopularRepoAdapter(list, this)
        rvApp.adapter = listAppAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}