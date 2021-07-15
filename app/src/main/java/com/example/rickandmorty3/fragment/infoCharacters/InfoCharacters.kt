package com.example.rickandmorty3.fragment.infoCharacters

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.rickandmorty3.R
import com.example.rickandmorty3.fragment.CharactersViewModel


class InfoCharacters : Fragment(R.layout.fragment_info_characters) {

    lateinit var imageView: ImageView
    lateinit var name: TextView
    lateinit var status: TextView
    lateinit var species: TextView
    lateinit var gender: TextView
    lateinit var type: TextView
    lateinit var origin: TextView
    lateinit var location: TextView
    lateinit var button: Button

    private val viewModel: CharactersViewModel by activityViewModels()

    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        configureObservers(view)
    }

    private fun configureObservers(view: View) {
        viewModel.selectedHero.observe(viewLifecycleOwner) {
            name.text = it.name
            status.text = it.status
            species.text = it.species
            gender.text = it.gender
            type.text = it.type
            val originName: String = it.origin?.name.toString()
            origin.text = originName
            val locationName: String = it.location?.name.toString()
            location.text = locationName
            Glide.with(view.context)
                .load(it.image)
                .into(imageView)
        }
        button.setOnClickListener { navController.navigateUp()
        }
    }

    fun initView(root: View){
        imageView = root.findViewById(R.id.imagePers3)
        name = root.findViewById(R.id.create_name)
        status = root.findViewById(R.id.create_status)
        species = root.findViewById(R.id.create_species)
        gender = root.findViewById(R.id.create_gender)
        type = root.findViewById(R.id.create_type)
        origin = root.findViewById(R.id.create_original)
        location = root.findViewById(R.id.create_location)
        button = root.findViewById(R.id.back_character_list)
    }
}