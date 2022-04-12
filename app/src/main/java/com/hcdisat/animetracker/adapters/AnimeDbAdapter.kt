package com.hcdisat.animetracker.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hcdisat.animetracker.R
import com.hcdisat.animetracker.databinding.AnimeItemBinding
import com.hcdisat.animetracker.models.transformers.AnimeAndEpisodes

class AnimeDbAdapter(
    private var animes: List<AnimeAndEpisodes> = listOf(),
    private val onAnimeClicked: (anime: AnimeAndEpisodes) -> Unit
) : RecyclerView.Adapter<AnimeDbItemViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setAnimeList(newList: List<AnimeAndEpisodes>) {
        animes = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeDbItemViewHolder =
        AnimeDbItemViewHolder(
            AnimeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onAnimeClicked
        )

    override fun onBindViewHolder(holder: AnimeDbItemViewHolder, position: Int) =
        holder.bind(animes[position])

    override fun getItemCount(): Int = animes.size
}

class AnimeDbItemViewHolder(
    private val binding: AnimeItemBinding,
    private val onAnimeClicked: (anime: AnimeAndEpisodes) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(animeAndEpisodes: AnimeAndEpisodes) {
        binding.animeNameText.text = animeAndEpisodes.anime.titleEn
        binding.animeNameOriginalText.text = animeAndEpisodes.anime.titleJa

        Glide.with(binding.root)
            .load(animeAndEpisodes.anime.coverImage)
            .optionalCenterCrop()
            .placeholder(R.drawable.ic_itadori)
            .into(binding.poster)

        binding.root.setOnClickListener { onAnimeClicked(animeAndEpisodes)}
    }
}