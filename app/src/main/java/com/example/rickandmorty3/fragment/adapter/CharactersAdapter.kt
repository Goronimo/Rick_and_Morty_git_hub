package com.example.rickandmorty3.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty3.R
import com.example.rickandmorty3.domain.model.CharacterDomainModel

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.MyViewHolder>() {

    private val characterApiModels: MutableList<CharacterDomainModel> = mutableListOf()
    lateinit var onCardClickListener: OnCardClickListener

    fun replaceItem(newCharacterApiModels: List<CharacterDomainModel>, onCardClickListener: OnCardClickListener) {
        val oldSize = characterApiModels.size
        characterApiModels.clear()
        characterApiModels.addAll(newCharacterApiModels)
        this.onCardClickListener = onCardClickListener
        notifyItemRangeInserted(oldSize, newCharacterApiModels.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.character_list_item, parent, false)
        return MyViewHolder(v, onCardClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val personage: CharacterDomainModel = characterApiModels[position]
        holder.name.text = personage.name
        Glide.with(holder.imagePersonage.context)
            .load(personage.image)
            .into(holder.imagePersonage)
    }

    override fun getItemCount(): Int {
        return characterApiModels.size
    }

    inner class MyViewHolder(itemView: View, var onCardClickListener: OnCardClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var name: TextView
        var imagePersonage: ImageView
        override fun onClick(v: View) {
            onCardClickListener.onCardClick(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
            name = itemView.findViewById(R.id.tv_character_name)
            imagePersonage = itemView.findViewById(R.id.iv_character)
        }
    }

    interface OnCardClickListener {
        fun onCardClick(position: Int)
    }
}