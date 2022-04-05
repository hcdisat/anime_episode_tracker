package com.hcdisat.animetracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hcdisat.animetracker.MainActivity
import com.hcdisat.animetracker.R

class PlayGroundFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as MainActivity).apply {
            appBarLayout.setExpanded(false)
            collapsingToolbar.title = getString(R.string.app_name)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_ground, container, false)
    }
}