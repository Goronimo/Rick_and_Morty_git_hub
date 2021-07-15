package com.example.rickandmorty3.domain.model

data class LocationDomainModel(
    val id: Int = 0,
    val name: String,
    val type: String,
    val dimension: String,
    val url: String
)
