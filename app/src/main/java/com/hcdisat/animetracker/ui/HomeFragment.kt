package com.hcdisat.animetracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hcdisat.animetracker.MainActivity
import com.hcdisat.animetracker.R
import com.hcdisat.animetracker.adapters.AnimeAdapter
import com.hcdisat.animetracker.databinding.FragmentHomeBinding
import com.hcdisat.animetracker.models.Anime
import com.hcdisat.animetracker.models.AnimeResponse
import com.hcdisat.animetracker.viewmodels.AnimeViewModel
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val trendingAnimeAdapter by lazy { AnimeAdapter() }
    private val mostPopularAnimeAdapter by lazy { AnimeAdapter() }
    private val mostRatedAnimeAdapter by lazy { AnimeAdapter() }

    private val animeViewModel: AnimeViewModel by activityViewModels()

    private fun bindAnimeLists() {
        setAnimeRecycler(binding.trending.animeList, trendingAnimeAdapter)
        setAnimeRecycler(binding.popular.animeList, mostPopularAnimeAdapter)
        setAnimeRecycler(binding.mostRated.animeList, mostPopularAnimeAdapter)
    }

    private fun setAnimeRecycler(recyclerView: RecyclerView, animeAdapter: AnimeAdapter) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            adapter = animeAdapter
        }
    }

    private fun handleStateChanged(animeState: AnimeState) = when(animeState) {
        is AnimeState.LOADING -> {}
        is AnimeState.ERROR -> throw animeState.throwable
        is AnimeState.SUCCESS<*> -> bindData(animeState)
    }

    private fun <T> bindData(animeState: AnimeState.SUCCESS<T>) {
        val animes = (animeState.response as AnimeResponse).data
        when(animeState.section) {
            AnimeSections.TRENDING -> {
                setCoverInfo(animes[0])
                binding.trending.sectionText.text = animeState.section.realName
                trendingAnimeAdapter.setAnimeList(animes)
            }
            AnimeSections.MOST_POPULAR -> {
                binding.popular.sectionText.text = animeState.section.realName
                mostPopularAnimeAdapter.setAnimeList(animes)
            }

            AnimeSections.MOST_RATED -> {
                binding.mostRated.sectionText.text = animeState.section.realName
                mostPopularAnimeAdapter.setAnimeList(animes)
            }
        }

    }

    private fun loadBackDrop(imageUrl: String) {
        Glide.with(this )
            .load(imageUrl)
            .apply(RequestOptions.centerCropTransform())
            .placeholder(R.drawable.ic_itadori_cover)
            .into(binding.numberOneCover)
    }

    private fun setCoverInfo(anime: Anime) {
//        val activity = requireActivity() as MainActivity
        binding.collapsingToolbar.title = anime.attributes.titles.enJp
        loadBackDrop(anime.attributes.coverImage.small)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        bindAnimeLists()

        animeViewModel.state.observe(viewLifecycleOwner, ::handleStateChanged)

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