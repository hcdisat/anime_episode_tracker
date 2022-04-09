package com.hcdisat.animetracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.hcdisat.animetracker.MainActivity
import com.hcdisat.animetracker.databinding.FragmentAnimeDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeDetailsFragment : AnimeBaseFragment() {

    private var _binding: FragmentAnimeDetailsBinding? = null
    private val binding: FragmentAnimeDetailsBinding get() = _binding!!

    private fun prepareActivity() {
        (requireActivity() as MainActivity).apply {
            setCoverInfo()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailsBinding
            .inflate(inflater, container, false)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        // Inflate the layout for this fragment

        prepareActivity()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }
}