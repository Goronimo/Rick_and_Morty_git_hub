package com.example.rickandmorty3.data.model.api

import com.example.rickandmorty3.data.model.api.EpisodeApiModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EpisodeListApiModel(
    @SerializedName("results")
    @Expose
    val results: MutableList<EpisodeApiModel>? = null
)
