package com.hcdisat.animetracker.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hcdisat.animetracker.R
import com.hcdisat.animetracker.databinding.AnimeItemBinding
import com.hcdisat.animetracker.models.Anime

class AnimeAdapter(
    private var animes: List<Anime> = listOf(),
    private val onAnimeClicked: (anime: Anime) -> Unit
) : RecyclerView.Adapter<AnimeItemViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setAnimeList(newList: List<Anime>) {
        animes = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeItemViewHolder =
        AnimeItemViewHolder(
            AnimeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onAnimeClicked
        )

    override fun onBindViewHolder(holder: AnimeItemViewHolder, position: Int) =
        holder.bind(animes[position])

    override fun getItemCount(): Int = animes.size
}

class AnimeItemViewHolder(
    private val binding: AnimeItemBinding,
    private val onAnimeClicked: (anime: Anime) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(anime: Anime) {
        binding.animeNameText.text = anime.attributes.titles.enJp
        binding.animeNameOriginalText.text = anime.attributes.titles.jaJp

        Glide.with(binding.root)
            .load(anime.attributes.posterImage.small)
            .optionalCenterCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.ic_itadori)
            .into(binding.poster)

        binding.root.setOnClickListener { onAnimeClicked(anime)}
    }
}