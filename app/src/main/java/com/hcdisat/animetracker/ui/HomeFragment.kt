package com.hcdisat.animetracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcdisat.animetracker.MainActivity
import com.hcdisat.animetracker.adapters.HomeSectionAdapter
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

    private val sectionAdapter by lazy { HomeSectionAdapter() }

    private val animeViewModel: AnimeViewModel by activityViewModels()

    private fun bindAnimeLists() {
        binding.homeSections.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

            adapter = sectionAdapter
        }
    }

    private fun handleStateChanged(animeState: AnimeState) = when (animeState) {
        is AnimeState.LOADING -> {}
        is AnimeState.ERROR -> throw animeState.throwable
        is AnimeState.SUCCESS -> {
            if (animeState.section == AnimeSections.TRENDING) {
                val animeResponse = animeState.response
                setCoverInfo(animeResponse.data[0])
            }
            sectionAdapter.appendSection(animeState)
        }
    }

    private fun setCoverInfo(anime: Anime) {
        (requireActivity() as MainActivity).apply {
            collapsingToolbar.title = anime.attributes.titles.enJp
            loadBackDrop(anime.attributes.coverImage.small)
        }
    }

    private fun softReload() {
        animeViewModel.animeSectionData.apply {
            (trending as AnimeState.SUCCESS).let {
                setCoverInfo(it.response.data[0])
                sectionAdapter.setAdapter(it)
            }

            sectionAdapter.appendSection((popular as AnimeState.SUCCESS))
            sectionAdapter.appendSection((rated as AnimeState.SUCCESS))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        bindAnimeLists()

        animeViewModel.state.observe(viewLifecycleOwner, ::handleStateChanged)

        binding

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        when (animeViewModel.state.value) {
            is AnimeState.SUCCESS -> softReload()
            else -> animeViewModel.getTrending()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}