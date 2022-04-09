package com.hcdisat.animetracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hcdisat.animetracker.databinding.SectionItemBinding
import com.hcdisat.animetracker.models.Anime
import com.hcdisat.animetracker.models.AnimeResponse
import com.hcdisat.animetracker.viewmodels.state.AnimeState

class HomeSectionAdapter(
    private var sections: MutableList<AnimeState.SUCCESS> = mutableListOf(),
    private val onAnimeClicked: (anime: Anime) -> Unit
): RecyclerView.Adapter<HomeSectionViewHolder>() {

    fun appendSection(newSection: AnimeState.SUCCESS) {
        sections.add(newSection)
        notifyItemInserted(itemCount - 1)
    }

    fun setAdapter(newSection: AnimeState.SUCCESS) {
        sections = mutableListOf()
        sections.add(newSection)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSectionViewHolder =
        HomeSectionViewHolder(
            SectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onAnimeClicked
        )

    override fun onBindViewHolder(holder: HomeSectionViewHolder, position: Int) =
        holder.bind(sections[position])

    override fun getItemCount(): Int = sections.size
}

class HomeSectionViewHolder(
    private val binding: SectionItemBinding,
    private val onAnimeClicked: (anime: Anime) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    private val animeAdapter by lazy {
        AnimeAdapter(onAnimeClicked = onAnimeClicked)
    }

    fun bind(animeState: AnimeState.SUCCESS) {
        animeAdapter.setAnimeList((animeState.response).data)
        binding.sectionText.text = animeState.section.realName
        binding.animeList.apply {
            layoutManager = LinearLayoutManager(
                binding.root.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            adapter = animeAdapter
        }
    }
}