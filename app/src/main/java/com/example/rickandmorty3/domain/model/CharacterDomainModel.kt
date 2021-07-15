package com.example.rickandmorty3.domain.model

data class CharacterDomainModel(
    val id: Int = 0,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginDomainModel,
    val location: LocationDomainModel,
    val image: String,
) {
    data class OriginDomainModel(
        val name: String
    )

    data class LocationDomainModel(
        val name: String
    )
}
