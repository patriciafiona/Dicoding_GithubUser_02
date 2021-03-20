package com.path_studio.githubuser.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.path_studio.githubuser.R
import com.path_studio.githubuser.Utils
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

    private lateinit var skeleton: Skeleton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val view = binding.root

        //init Skeleton
        skeleton = binding.rvDiscover.applySkeleton(R.layout.item_col_trending_repo)
        skeleton.maskCornerRadius = 20f

        showLoading(true)

        //Set Animation
        settingAnimation()

        //Get data & set trending recycle view
        getTrendingFromAPI()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Show Search Bar
        (activity as MainActivity).setSearchBarVisibility(1)
        (activity as MainActivity).clearSearchBar()
    }

    private fun settingAnimation(){
        //Setting trending banner image
        val image: ImageView = binding.trendingBannerImg
        val fadeImgAnim: Animation = AnimationUtils.loadAnimation((activity as MainActivity).applicationContext, R.anim.fade_in)
        image.startAnimation(fadeImgAnim)

        //Setting trending banner Text
        val text: TextView = binding.trendingBannerText
        val fadeTxtAnim: Animation = AnimationUtils.loadAnimation((activity as MainActivity).applicationContext, R.anim.fade_in_and_slide)
        fadeTxtAnim.startOffset = 500
        text.startAnimation(fadeTxtAnim)
    }

    private fun getTrendingFromAPI(){
        //get last week date
        val lastWeek:String = Utils.getDaysAgo(7)
        val query = "created:>$lastWeek"

        CreateAPI.create().getTrendingRepo(query, ProfileFragment.ACCESS_TOKEN).enqueue(object : Callback<SearchRepo> {
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
                Utils.showFailedGetDataFromAPI(activity as MainActivity)
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

    private fun showLoading(state: Boolean) {
        if (state) {
            skeleton.showSkeleton()
        } else {
            skeleton.showOriginal()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}