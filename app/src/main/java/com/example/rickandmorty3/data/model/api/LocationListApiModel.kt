package com.example.rickandmorty3.data.model.api

import com.example.rickandmorty3.data.model.api.LocationApiModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationListApiModel(
    @SerializedName("results")
    @Expose
    val results: MutableList<LocationApiModel>? = null
)