package com.path_studio.githubuser.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.path_studio.githubuser.R
import com.path_studio.githubuser.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnCLick()
    }

    private fun setOnCLick(){
        val fbButtonIcon: ImageButton = binding.smFacebook
        val igIconButton: ImageButton = binding.smInstagram
        val githubIconButton: ImageButton = binding.smGithub
        val linkedInIconButton: ImageButton = binding.smLinkedIn

        fbButtonIcon.setOnClickListener{
            goToUrl("https://www.facebook.com/patricia.fiona.7")
        }

        igIconButton.setOnClickListener {
            goToUrl("https://www.instagram.com/path.patricia15/")
        }

        githubIconButton.setOnClickListener {
            goToUrl("https://github.com/patriciafiona")
        }

        linkedInIconButton.setOnClickListener {
            goToUrl("https://www.linkedin.com/in/patricia-fiona-6132861a3/")
        }
    }

    private fun goToUrl(url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}