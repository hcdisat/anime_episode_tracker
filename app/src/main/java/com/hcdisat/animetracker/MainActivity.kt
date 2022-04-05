package com.hcdisat.animetracker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hcdisat.animetracker.databinding.ActivityMainBinding
import com.hcdisat.animetracker.viewmodels.AnimeViewModel
import dagger.hilt.android.AndroidEntryPoint


const val TAG = "TAG"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val animeViewModel: AnimeViewModel by viewModels()

    val collapsingToolbar by lazy {
        binding.collapsingToolbar
    }

    val appBarLayout by lazy {
        binding.appBarLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        setupActionBarWithNavController(
            navHostFragment.navController, AppBarConfiguration(
                setOf(
                    R.id.homeFragment
                )
            )
        )

        binding.navView.setupWithNavController(navHostFragment.navController)

        binding.btnPlay.setOnClickListener {
            val videoId = "Fee5vbFLYM4"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
            intent.putExtra("VIDEO_ID", videoId)
            startActivity(intent)
        }
    }

    fun loadBackDrop(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .apply(RequestOptions.centerCropTransform())
            .into(binding.numberOneCover)
    }
}
