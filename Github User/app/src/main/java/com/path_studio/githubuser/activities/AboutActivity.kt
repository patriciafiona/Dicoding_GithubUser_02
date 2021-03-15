package com.path_studio.githubuser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.path_studio.githubuser.R
import com.path_studio.githubuser.databinding.ActivityAboutBinding
import com.path_studio.githubuser.databinding.ActivitySettingsBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}