package com.example.rickandmorty3.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationApiModel(

    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("type")
    @Expose
    val type: String? = null,

    @SerializedName("dimension")
    @Expose
    val dimension: String? = null,

    @SerializedName("url")
    @Expose
    val url: String? = null
)