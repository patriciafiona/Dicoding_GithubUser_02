package com.path_studio.githubuser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.path_studio.githubuser.R
import com.path_studio.githubuser.databinding.ActivityAccountBinding
import com.path_studio.githubuser.databinding.ActivitySettingsBinding

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}