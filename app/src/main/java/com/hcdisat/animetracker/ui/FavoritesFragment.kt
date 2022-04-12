package com.hcdisat.animetracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.hcdisat.animetracker.adapters.AnimeDbAdapter
import com.hcdisat.animetracker.databinding.FragmentFavoritesBinding
import com.hcdisat.animetracker.viewmodels.state.DBOperationsState

class FavoritesFragment : AnimeBaseFragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding get() = _binding!!

    private val animeAdapter by lazy {
        AnimeDbAdapter() {
            AnimeDetailsFragment
                .newAnimeDetailsFragment(it)
                .show(childFragmentManager, AnimeDetailsFragment.TAG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        binding.favoriteList.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2, GridLayoutManager.VERTICAL, false)
            adapter = animeAdapter

            animeViewModel.favoriteState.observe(viewLifecycleOwner) {
                when(it) {
                    is DBOperationsState.RESULTS -> {
                        animeAdapter.setAnimeList(it.animes)
                    }
                    is DBOperationsState.LOADING -> {}
                    else -> {}
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        animeViewModel.loadFavorites()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}