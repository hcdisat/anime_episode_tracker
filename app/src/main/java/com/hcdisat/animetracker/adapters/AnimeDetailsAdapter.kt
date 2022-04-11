package com.hcdisat.animetracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hcdisat.animetracker.R
import com.hcdisat.animetracker.databinding.FragmentAnimeDetailsBinding
import com.hcdisat.animetracker.models.transformers.AnimeAndEpisodes
import com.hcdisat.animetracker.viewmodels.state.DBOperationsState
import kotlinx.coroutines.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class AnimeDetailsAdapter(
    private val animePresenter: AnimeAndEpisodes,
    private val onFavoriteClick: (animeAndEpisodes: AnimeAndEpisodes) -> Flow<DBOperationsState>
) : RecyclerView.Adapter<AnimeDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeDetailsViewHolder =
        AnimeDetailsViewHolder(
            FragmentAnimeDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onFavoriteClick
        )

    override fun onBindViewHolder(holder: AnimeDetailsViewHolder, position: Int) =
        holder.bind(animePresenter)

    override fun getItemCount(): Int = 1
}

class AnimeDetailsViewHolder(
    private val binding: FragmentAnimeDetailsBinding,
    private val onFavoriteClick: (animeAndEpisodes: AnimeAndEpisodes) -> Flow<DBOperationsState>
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(animeTransformer: AnimeAndEpisodes) {
        animeTransformer.anime.apply {
            binding.animeTitle.text = titleEn
            binding.animeTitleJp.text = titleJa
            binding.ratingRank.text = ratingRank.toString()
            binding.animeAgeRatingGuide.text = ageRatingGuide
            binding.animeStatus.text = status
            binding.animeDescription.text = description

            binding.episodeList.apply {
                layoutManager = LinearLayoutManager(
                    binding.root.context,
                    LinearLayoutManager.VERTICAL, false)
                adapter = EpisodeAdapter(animeTransformer.episodes)
            }

            binding.action.setOnClickListener { view ->
                CoroutineScope(Dispatchers.Main).launch {
                    onFavoriteClick(animeTransformer).collect {
                        (view as ImageButton).setImageResource(
                            when(it) {
                                is DBOperationsState.SAVED -> R.drawable.ic_unfavorite
                                is DBOperationsState.REMOVED -> R.drawable.ic_favorite
                            }
                        )
                    }
                }
            }
        }
    }
}