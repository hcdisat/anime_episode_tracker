package com.hcdisat.animetracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hcdisat.animetracker.databinding.FragmentAnimeDetailsBinding
import com.hcdisat.animetracker.models.transformers.AnimeAndEpisodes

class AnimeDetailsAdapter(
    private var animePresenter: AnimeAndEpisodes? = null,
    private val onFavoriteClick: (animeAndEpisodes: AnimeAndEpisodes) -> Unit
) : RecyclerView.Adapter<AnimeDetailsViewHolder>() {

    fun setAnimeAndEpisodes(animeAndEpisodes: AnimeAndEpisodes) {
        animePresenter = animeAndEpisodes
        notifyItemChanged(0)
    }

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
    private val onFavoriteClick: (animeAndEpisodes: AnimeAndEpisodes) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(animeTransformer: AnimeAndEpisodes?) {
        animeTransformer?.anime?.let {
            binding.animeTitle.text = it.titleEn
            binding.animeTitleJp.text = it.titleJa
            binding.ratingRank.text = it.ratingRank.toString()
            binding.animeAgeRatingGuide.text = it.ageRatingGuide
            binding.animeStatus.text = it.status
            binding.animeDescription.text = it.description

            binding.episodeList.apply {
                layoutManager = LinearLayoutManager(
                    binding.root.context,
                    LinearLayoutManager.VERTICAL, false
                )
                adapter = EpisodeAdapter(animeTransformer.episodes)
            }

            binding.favoriteSwitch.isChecked = animeTransformer.anime.saved

            binding.favoriteSwitch.setOnCheckedChangeListener {_, _ ->
                onFavoriteClick(animeTransformer)
            }
        }
    }
}