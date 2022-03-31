package com.hcdisat.animetracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hcdisat.animetracker.databinding.FragmentHomeBinding
import com.hcdisat.animetracker.viewmodels.AnimeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val animeViewModel: AnimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        animeViewModel.getTrending()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}