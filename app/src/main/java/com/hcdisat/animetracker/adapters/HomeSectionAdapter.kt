package com.hcdisat.animetracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hcdisat.animetracker.databinding.SectionItemBinding

class HomeSectionAdapter(
    private var sections: AdapterSections,
): RecyclerView.Adapter<HomeSectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSectionViewHolder =
        HomeSectionViewHolder(
            SectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )

    override fun onBindViewHolder(holder: HomeSectionViewHolder, position: Int) =
        holder.bind(sections)

    override fun getItemCount(): Int = 1
}

class HomeSectionViewHolder(
    private val binding: SectionItemBinding,
): RecyclerView.ViewHolder(binding.root) {

    fun bind(sections: AdapterSections) {
        binding.trendingList.apply {
            layoutManager = createLayoutManager()
            adapter = sections.trending
        }

        binding.popularList.apply {
            layoutManager = createLayoutManager()
            adapter = sections.mostPopular
        }

        binding.mostRatedList.apply {
            layoutManager = createLayoutManager()
            adapter = sections.mostRated
        }
    }

    private fun createLayoutManager() =
        LinearLayoutManager(
            binding.root.context,
            LinearLayoutManager.HORIZONTAL,
            false)
}

data class AdapterSections(
    val trending: AnimeAdapter,
    val mostPopular: AnimeAdapter,
    val mostRated: AnimeAdapter
)