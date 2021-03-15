package com.path_studio.githubuser.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.path_studio.githubuser.R
import com.path_studio.githubuser.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //change Navigation Title
        this.actionBar?.title = resources.getString(R.string.settings)
        supportActionBar?.title = resources.getString(R.string.settings)

        //set Onclick on Settings Menu
        setSettingsMenuListener()
    }

    private fun setSettingsMenuListener(){
        //For Account Menu
        binding.textAccount.setOnClickListener {
            val i = Intent(this, AccountActivity::class.java)
            startActivity(i)
        }

        //For About Menu
        binding.textAbout.setOnClickListener {
            val i = Intent(this, AboutActivity::class.java)
            startActivity(i)
        }

        //For Language Menu
        binding.textLanguage.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
    }

}