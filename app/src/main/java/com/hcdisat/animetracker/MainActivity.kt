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
import com.hcdisat.animetracker.databinding.ActivityMainBinding
import com.hcdisat.animetracker.models.Anime
import com.hcdisat.animetracker.viewmodels.AnimeViewModel
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "TAG"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val animeViewModel: AnimeViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        setupActionBarWithNavController(
            navHostFragment.navController, AppBarConfiguration(
                setOf(
                    R.id.homeFragment,
                    R.id.favorites_fragment
                )
            )
        )

        binding.navView.setupWithNavController(navHostFragment.navController)
        animeViewModel.playAction = ::playTrailer
    }

    private fun playTrailer() {
        animeViewModel.selectedAnime?.let {
            val videoId = it.attributes.youtubeVideoId
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
            intent.putExtra("VIDEO_ID", videoId)
            startActivity(intent)
        }
    }
}
