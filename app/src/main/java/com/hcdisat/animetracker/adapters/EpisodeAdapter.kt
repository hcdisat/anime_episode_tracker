package com.hcdisat.animetracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hcdisat.animetracker.databinding.EpisodeItemBinding
import com.hcdisat.animetracker.models.transformers.EpisodeTransformer

class EpisodeAdapter(
    private val episodes: List<EpisodeTransformer>
    ): RecyclerView.Adapter<EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder =
        EpisodeViewHolder(
            EpisodeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) =
        holder.bind(episodes[position])

    override fun getItemCount(): Int = episodes.size
}

class EpisodeViewHolder(
    private val binding: EpisodeItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(episode: EpisodeTransformer) {
        episode.run {
            Glide.with(binding.root.context)
                .load(thumbnail)
                .centerCrop()
                .into(binding.thumbnail)

            binding.title.text = title
            binding.seasonNumber.text = seasonNumber.toString()
            binding.duration.text = duration.toString()
        }
    }
}