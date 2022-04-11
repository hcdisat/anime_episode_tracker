package com.hcdisat.animetracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hcdisat.animetracker.databinding.BannerBinding

class BannerAdapter(
    private var bannerData: BannerData? = null
) : RecyclerView.Adapter<BannerViewHolder>() {

    fun setBanner(newBannerData: BannerData) {
        bannerData = newBannerData
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder =
        BannerViewHolder(
            BannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) =
        holder.bind(bannerData)

    override fun getItemCount(): Int = 1
}

class BannerViewHolder(
    private val binding: BannerBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(bannerData: BannerData?) {
        bannerData?.let { banner ->
            if (banner.shouldShowControlBar) {
                binding.dismiss.setOnClickListener { banner.onDismiss() }
                binding.controlGroup.visibility = View.VISIBLE
            }
            binding.btnPlay.setOnClickListener { banner.onPlay.invoke() }

            Glide.with(binding.root.context)
                .load(banner.cover)
                .centerCrop()
                .into(binding.imgCover)
        }
    }
}

data class BannerData(
    val cover: String,
    val shouldShowControlBar: Boolean,
    inline var onPlay: () -> Unit = {},
    inline var onDismiss: () -> Unit = { },
)