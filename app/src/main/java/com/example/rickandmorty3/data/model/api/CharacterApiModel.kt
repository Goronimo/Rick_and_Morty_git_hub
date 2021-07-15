package com.example.rickandmorty3.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CharacterApiModel(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("status")
    @Expose
    val status: String? = null,

    @SerializedName("species")
    @Expose
    val species: String? = null,

    @SerializedName("type")
    @Expose
    val type: String? = null,

    @SerializedName("gender")
    @Expose
    val gender: String? = null,

    @SerializedName("origin")
    @Expose
    val origin: OriginApiModel? = null,

    @SerializedName("location")
    @Expose
    val location: LocationApiModel? = null,

    @SerializedName("image")
    @Expose
    val image: String? = null,
) {
    data class OriginApiModel(
        @SerializedName("name")
        @Expose
        val name: String? = null
    )

    data class LocationApiModel(
        @SerializedName("name")
        @Expose
        val name: String? = null
    )
}
