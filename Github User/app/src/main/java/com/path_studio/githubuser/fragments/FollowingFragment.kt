package com.path_studio.githubuser.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.path_studio.githubuser.Utils
import com.path_studio.githubuser.activities.DetailFollowActivity
import com.path_studio.githubuser.activities.DetailUserActivity
import com.path_studio.githubuser.activities.MainActivity
import com.path_studio.githubuser.adapters.ListFollowAdapter
import com.path_studio.githubuser.adapters.ListPopularRepoAdapter
import com.path_studio.githubuser.databinding.FragmentFollowingBinding
import com.path_studio.githubuser.models.CreateAPI
import com.path_studio.githubuser.models.Repository
import com.path_studio.githubuser.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {

    private lateinit var listFollowing:ArrayList<User>

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root

        //show Loading
        showLoading(true)

        //get list following
        getFollowingFromAPIAndShow()

        return view
    }

    private fun getFollowingFromAPIAndShow(){
        CreateAPI.create().getUserFollowing(
            DetailFollowActivity.USERNAME,
            ProfileFragment.ACCESS_TOKEN
        ).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    listFollowing = response.body() as ArrayList<User>

                    //show recycle view
                    showRV()

                    //hide loading indicator
                    showLoading(false)
                }
            }

            override fun onFailure(call: Call<List<User>>, error: Throwable) {
                Log.e("tag", "The Error is: ${error.message}")
                Utils.showFailedGetDataFromAPI(activity as DetailUserActivity)
            }
        })

    }

    private fun showRV(){
        val rvListFollowing: RecyclerView = binding.rvListFollowing
        rvListFollowing.setHasFixedSize(true)

        showRecyclerList(rvListFollowing, listFollowing)
    }

    private fun showRecyclerList(rv: RecyclerView, list: ArrayList<User>) {
        rv.layoutManager = LinearLayoutManager(activity)
        val listAppAdapter = ListFollowAdapter(list, activity as DetailFollowActivity)
        rv.adapter = listAppAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}