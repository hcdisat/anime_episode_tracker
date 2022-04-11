package com.hcdisat.animetracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcdisat.animetracker.R
import com.hcdisat.animetracker.adapters.AnimeDetailsAdapter
import com.hcdisat.animetracker.adapters.BannerAdapter
import com.hcdisat.animetracker.adapters.BannerData
import com.hcdisat.animetracker.databinding.AnimeDetailsContainerBinding
import com.hcdisat.animetracker.models.episodes.EpisodesResponse
import com.hcdisat.animetracker.models.transformers.AnimeAndEpisodes
import com.hcdisat.animetracker.models.transformers.AnimeTransformer
import com.hcdisat.animetracker.models.transformers.EpisodeTransformer
import com.hcdisat.animetracker.models.transformers.episodeName
import com.hcdisat.animetracker.viewmodels.AnimeViewModel
import com.hcdisat.animetracker.viewmodels.state.DBOperationsState
import com.hcdisat.animetracker.viewmodels.state.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeDetailsFragment : DialogFragment() {

    private var _binding: AnimeDetailsContainerBinding? = null
    private val binding: AnimeDetailsContainerBinding get() = _binding!!

    private val concatAdapter by lazy { ConcatAdapter() }

    private lateinit var anime: AnimeTransformer

    private val episodes: MutableList<EpisodeTransformer> = mutableListOf()

    private val animeViewModel: AnimeViewModel by activityViewModels()

    private fun setRecycler() {
        concatAdapter.addAdapter(0, BannerAdapter(
            BannerData(
                animeViewModel.selectedAnime?.attributes?.posterImage?.small ?: "",
                true,
                animeViewModel.playAction
            ) { dismissAllowingStateLoss() }
        ))

        binding.animeDetails.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = concatAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AnimeDetailsContainerBinding
            .inflate(inflater, container, false)

        animeViewModel.selectedAnime?.let {
            anime = AnimeTransformer(
                animeId = it.id,
                titleEn = it.attributes.titles.en,
                titleJa = it.attributes.titles.jaJp,
                ratingRank = it.attributes.ratingRank,
                ageRatingGuide = it.attributes.ageRatingGuide,
                description = it.attributes.description,
                status = it.attributes.status
            )
        }
        setRecycler()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        animeViewModel
            .getEpisodes(anime.animeId)
            .observe(viewLifecycleOwner, ::handleState)
    }

    private fun handleState(uiState: UIState) {
        when (uiState) {
            is UIState.LOADING -> {}
            is UIState.ERROR -> ErrorDialog
                .newErrorDialog(uiState.throwable.localizedMessage ?: "")
                .show(childFragmentManager, ErrorDialog.TAG)
            is UIState.SUCCESS<*> -> {
                (uiState.response as EpisodesResponse)
                    .episodes.map {
                        episodes.add(EpisodeTransformer(
                            id = it.id,
                            animeId = anime.animeId,
                            title = it.attributes.episodeName(),
                            thumbnail = it.attributes.thumbnail?.original ?: "",
                            seasonNumber = it.attributes.seasonNumber,
                            duration = it.attributes.length
                        ))
                    }
                concatAdapter.addAdapter(
                    AnimeDetailsAdapter(
                        AnimeAndEpisodes(anime, episodes)
                    ) { anime ->
                        animeViewModel.saveAnime(anime)
                    }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "AnimeDetailsFragment"

        fun newAnimeDetailsFragment() = AnimeDetailsFragment().apply {
            setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        }
    }
}