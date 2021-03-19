package com.path_studio.githubuser.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.path_studio.githubuser.R
import com.path_studio.githubuser.activities.DetailFollowActivity
import com.path_studio.githubuser.activities.MainActivity
import com.path_studio.githubuser.adapters.ListFollowAdapter
import com.path_studio.githubuser.adapters.ListTrendingRepoAdapter
import com.path_studio.githubuser.databinding.FragmentExploreBinding
import com.path_studio.githubuser.databinding.FragmentProfileBinding
import com.path_studio.githubuser.models.CreateAPI
import com.path_studio.githubuser.models.Repository
import com.path_studio.githubuser.models.SearchRepo
import com.path_studio.githubuser.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private lateinit var TrendingRepo: SearchRepo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val view = binding.root

        showLoading(true)

        //Get data & set trending recycle view
        getTrendingFromAPI()

        return view
    }

    private fun getTrendingFromAPI(){
        CreateAPI.create().getTrendingRepo(ProfileFragment.ACCESS_TOKEN).enqueue(object : Callback<SearchRepo> {
            override fun onResponse(
                call: Call<SearchRepo>,
                response: Response<SearchRepo>
            ) {
                if (response.isSuccessful) {
                    TrendingRepo = response.body() as SearchRepo

                    //set recycle view
                    showRV()

                    showLoading(false)
                }
            }

            override fun onFailure(call: Call<SearchRepo>, t: Throwable) {
                Log.e("tag", "The Error is: ${t.message}")
            }

        })
    }

    private fun showRV(){
        val rvTrending: RecyclerView = binding.rvDiscover
        rvTrending.setHasFixedSize(true)

        showRecyclerList(rvTrending, TrendingRepo)
    }

    private fun showRecyclerList(rv: RecyclerView, list: SearchRepo) {
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val listAppAdapter = ListTrendingRepoAdapter(list, activity as MainActivity)
        rv.adapter = listAppAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Show Search Bar
        (activity as MainActivity).setSearchBarVisibility(1)
        (activity as MainActivity).clearSearchBar()
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