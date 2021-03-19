package com.path_studio.githubuser.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.path_studio.githubuser.activities.DetailFollowActivity
import com.path_studio.githubuser.adapters.ListFollowAdapter
import com.path_studio.githubuser.databinding.FragmentFollowerBinding
import com.path_studio.githubuser.models.CreateAPI
import com.path_studio.githubuser.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment : Fragment() {

    private lateinit var listFollower:ArrayList<User>

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        val view = binding.root

        //show Loading
        showLoading(true)

        //get list following
        getFollowerFromAPIAndShow()

        return view
    }

    private fun getFollowerFromAPIAndShow(){
        CreateAPI.create().getUserFollowers(
            DetailFollowActivity.USERNAME,
            ProfileFragment.ACCESS_TOKEN
        ).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    listFollower = response.body() as ArrayList<User>

                    //show recycle view
                    showRV()

                    //hide loading indicator
                    showLoading(false)
                }
            }

            override fun onFailure(call: Call<List<User>>, error: Throwable) {
                Log.e("tag", "The Error is: ${error.message}")
            }
        })

    }

    private fun showRV(){
        val rvListFollowing: RecyclerView = binding.rvListFollower
        rvListFollowing.setHasFixedSize(true)

        showRecyclerList(rvListFollowing, listFollower)
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