package com.hcdisat.animetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hcdisat.animetracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "TAG"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val collapsingToolbar by lazy {
        binding.collapsingToolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
//        setSupportActionBar(Toolbar(this))

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
    }

    fun loadBackDrop(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .apply(RequestOptions.centerCropTransform())
            .into(binding.numberOneCover)
    }
}
