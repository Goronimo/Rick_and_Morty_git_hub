package com.example.rickandmorty3.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EpisodeApiModel(
    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("air_date")
    @Expose
    val airDate: String? = null,

    @SerializedName("episode")
    @Expose
    val episode: String? = null
)