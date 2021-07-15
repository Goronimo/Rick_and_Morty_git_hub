package com.example.rickandmorty3.fragment.adapter

import android.animation.ValueAnimator
import android.animation.ValueAnimator.REVERSE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty3.R
import com.example.rickandmorty3.domain.model.EpisodeDomainModel

class EpisodeAdapter : RecyclerView.Adapter<EpisodeAdapter.MyViewHolder>() {
    private val episode: MutableList<EpisodeDomainModel> = mutableListOf()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.create_name_episode)
        var airData: TextView = itemView.findViewById(R.id.create_data_episode)
        var episode: TextView = itemView.findViewById(R.id.create_episod_episode)
    }

    fun replaceItem(newEpisode: List<EpisodeDomainModel>) {
        val oldSize = episode.size
        episode.clear()
        episode.addAll(newEpisode)
        notifyItemRangeInserted(oldSize, newEpisode.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.episode_list_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val episod: EpisodeDomainModel = episode[position]

        holder.itemView.translationX

        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.repeatMode = REVERSE
        animator.duration = 1000
        animator.addUpdateListener {
            holder.name.alpha = it.animatedFraction
        }
        animator.start()
        holder.name.text = episod.name
        holder.airData.text = episod.airDate
        holder.episode.text = episod.episode
    }

    override fun getItemCount(): Int {
        return episode.size
    }
}