package com.example.rickandmorty3.data.model.api

import com.example.rickandmorty3.data.model.api.CharacterApiModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CharactersListApiModel(
    @SerializedName("results")
    @Expose
    val results: MutableList<CharacterApiModel>? = null
)