package com.path_studio.githubuser.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.path_studio.githubuser.Utils
import com.path_studio.githubuser.adapters.ListNotificationAdapter
import com.path_studio.githubuser.databinding.ActivityNotificationBinding
import com.path_studio.githubuser.fragments.ProfileFragment
import com.path_studio.githubuser.models.CreateAPI
import com.path_studio.githubuser.models.Notification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    private lateinit var listNotification: ArrayList<Notification>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //show loading
        showLoading(true)

        //get Notification data from API
        getNotificationFromAPI()
    }

    private fun getNotificationFromAPI(){
        CreateAPI.create().getMyNotifications(ProfileFragment.ACCESS_TOKEN).enqueue(object :
            Callback<List<Notification>> {
            override fun onResponse(
                call: Call<List<Notification>>,
                response: Response<List<Notification>>
            ) {
                if (response.isSuccessful) {
                    listNotification = response.body() as ArrayList<Notification>

                    //show popular repo using horizontal recycle view
                    showNotifications()

                    //hide loading
                    showLoading(false)
                }
            }

            override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                Log.e("tag", "The Error is: ${t.message}")
                Utils.showFailedGetDataFromAPI(this@NotificationActivity)
            }

        })
    }

    private fun showNotifications(){
        val rvListNotification: RecyclerView = binding.rvListNotification
        rvListNotification.setHasFixedSize(true)

        showRecyclerList(rvListNotification, listNotification)
    }

    private fun showRecyclerList(rv: RecyclerView, list: ArrayList<Notification>) {
        rv.layoutManager = LinearLayoutManager(this)
        val listAppAdapter = ListNotificationAdapter(list, this)
        rv.adapter = listAppAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}