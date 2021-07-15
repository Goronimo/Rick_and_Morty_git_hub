package com.example.rickandmorty3.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty3.R
import com.example.rickandmorty3.data.model.api.LocationApiModel
import com.example.rickandmorty3.domain.model.LocationDomainModel

class LocationAdapter:RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {

    private val location: MutableList<LocationDomainModel> = mutableListOf()

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.create_name_location)
        var type: TextView = itemView.findViewById(R.id.create_type_location)
        var dimention: TextView = itemView.findViewById(R.id.create_dimension_location)
    }

    fun replaceItem(newLocation: List<LocationDomainModel>) {
        val oldSize = location.size
        location.clear()
        location.addAll(newLocation)
        notifyItemRangeInserted(oldSize, newLocation.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.location_list_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val location: LocationDomainModel = location[position]
        holder.name.text = location.name
        holder.type.text = location.type
        holder.dimention.text = location.dimension
    }

    override fun getItemCount(): Int {
        return location.size
    }
}