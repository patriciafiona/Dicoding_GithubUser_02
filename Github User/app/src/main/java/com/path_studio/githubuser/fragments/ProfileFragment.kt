package com.path_studio.githubuser.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.path_studio.githubuser.Utils
import com.path_studio.githubuser.activities.MainActivity
import com.path_studio.githubuser.databinding.FragmentProfileBinding
import com.path_studio.githubuser.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var listData: User
    private lateinit var listRepos: ArrayList<Repository>
    private lateinit var listOrgs: ArrayList<Organization>
    private lateinit var listStarred: ArrayList<Repository>

    private lateinit var gitHubServiceitHubService: GitHubService

    companion object {
        val MY_USERNAME = "patriciafiona"
        val ACCESS_TOKEN = "token 89f1eab6d166797a023fadcc7c2e51817831cde2"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        //show loading indicator
        showLoading(true)

        //init
        gitHubServiceitHubService = CreateAPI.create()

        //get Data From API
        getDataFromAPI()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Hide Search Bar
        (activity as MainActivity).setSearchBarVisibility(0)
        (activity as MainActivity).clearSearchBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDataFromAPI(){
        //get User Detail data from API
        getUserData()

        //get User Organization lits for get the total of organization
        getUserOrganization()

        //get User Repositories
        getMyRepository()

        //get My Starred Repo
        getMyStarredRepository()
    }

    private fun getUserData(){
        gitHubServiceitHubService.getUserDetail(MY_USERNAME, ACCESS_TOKEN).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    listData = response.body() as User

                    //Show Data
                    showData()

                    //hide loading indicator
                    showLoading(false)
                }
            }

            override fun onFailure(call: Call<User>, error: Throwable) {
                Log.e("tag", "The Error is: ${error.message}")
            }
        })
    }

    private fun getUserOrganization(){
        gitHubServiceitHubService.getUserOrganizations(MY_USERNAME, ACCESS_TOKEN).enqueue(object : Callback<List<Organization>> {
            override fun onResponse(
                call: Call<List<Organization>>,
                response: Response<List<Organization>>
            ) {
                if (response.isSuccessful) {
                    listOrgs = response.body() as ArrayList<Organization>

                    //show total repo
                    binding.myOrganizations.text = listOrgs.size.toString()

                }
            }

            override fun onFailure(call: Call<List<Organization>>, t: Throwable) {
                Log.e("tag", "The Error is: ${t.message}")
            }

        })
    }

    private fun getMyRepository(){
        gitHubServiceitHubService.getMyRepositories(ACCESS_TOKEN).enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    listRepos = response.body() as ArrayList<Repository>

                    //show total repo
                    binding.myRepository.text = listRepos.size.toString()

                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("tag", "The Error is: ${t.message}")
            }

        })
    }

    private fun getMyStarredRepository(){
        gitHubServiceitHubService.getUserStarredRepositories(MY_USERNAME, ACCESS_TOKEN).enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    listStarred = response.body() as ArrayList<Repository>

                    //show total repo
                    binding.myStarred.text = listStarred.size.toString()

                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("tag", "The Error is: ${t.message}")
            }

        })
    }

    private fun showData(){
        binding.myName.text = listData.name.toString()
        binding.myUsername.text = listData.login.toString()
        binding.myCompany.text = Utils.checkEmptyValue(listData.company.toString())
        binding.myLocation.text = Utils.checkEmptyValue(listData.location.toString())
        binding.myLink.text = Utils.checkEmptyValue(listData.blog.toString())
        binding.myFollowers.text = Utils.convertNumberFormat(listData.followers)
        binding.myFollowings.text = Utils.convertNumberFormat(listData.following)
        binding.myBio.text = Utils.checkEmptyValue(listData.bio.toString())

        Glide.with((activity as MainActivity).applicationContext)
            .load(listData.avatar_url)
            .apply(RequestOptions().override(500, 500))
            .into(binding.myAvatar)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}