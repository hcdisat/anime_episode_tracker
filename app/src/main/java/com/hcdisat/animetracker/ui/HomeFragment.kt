package com.hcdisat.animetracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcdisat.animetracker.adapters.*
import com.hcdisat.animetracker.databinding.FragmentHomeBinding
import com.hcdisat.animetracker.models.Anime
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : AnimeBaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val concatAdapter by lazy { ConcatAdapter() }
    private val bannerAdapter by lazy { BannerAdapter() }
    private val sections by lazy {
        AdapterSections(
            trendingAdapter,
            mostPopularAdapter,
            topRatedAdapter
        )
    }
    private val homeSectionAdapter by lazy { HomeSectionAdapter(sections) }
    private val trendingAdapter by lazy { AnimeAdapter(onAnimeClicked = ::onAnimeClicked) }
    private val mostPopularAdapter by lazy { AnimeAdapter(onAnimeClicked = ::onAnimeClicked) }
    private val topRatedAdapter by lazy { AnimeAdapter(onAnimeClicked = ::onAnimeClicked) }

    private fun onAnimeClicked(anime: Anime) {
        animeViewModel.selectedAnime = anime
        AnimeDetailsFragment
            .newAnimeDetailsFragment()
            .show(childFragmentManager, AnimeDetailsFragment.TAG)
    }

    private fun bindAnimeLists() {
        concatAdapter.addAdapter(bannerAdapter)
        concatAdapter.addAdapter(homeSectionAdapter)
        binding.homeSections.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = concatAdapter
        }
    }

    private fun handleStateChanged(animeState: AnimeState) = when (animeState) {
        is AnimeState.LOADING -> {
            binding.loading.visibility = View.VISIBLE
            binding.homeSections.visibility = View.GONE
        }
        is AnimeState.ERROR -> throw animeState.throwable
        is AnimeState.SUCCESS -> {
            when(animeState.section) {
                AnimeSections.TRENDING -> {
                    val animeResponse = animeState.response
                    animeViewModel.selectedAnime = animeResponse.data[0]
                    setBanner()
                    trendingAdapter.setAnimeList(animeState.response.data)
                }
                AnimeSections.MOST_POPULAR ->
                    mostPopularAdapter.setAnimeList(animeState.response.data)
                AnimeSections.MOST_RATED ->
                    topRatedAdapter.setAnimeList(animeState.response.data)
            }
            binding.loading.visibility = View.GONE
            binding.homeSections.visibility = View.VISIBLE
        }
    }

    private fun setBanner() {
        animeViewModel.selectedAnime?.let {
            bannerAdapter.setBanner(BannerData(
                it.attributes.coverImage.small,
                false,
                animeViewModel.playAction
            ))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        bindAnimeLists()

        binding
        animeViewModel.getTrending().observe(viewLifecycleOwner, ::handleStateChanged)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}