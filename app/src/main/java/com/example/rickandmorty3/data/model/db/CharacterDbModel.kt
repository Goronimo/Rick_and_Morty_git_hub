package com.example.rickandmorty3.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterDbModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "status")
    val status: String? = null,

    @ColumnInfo(name = "species")
    val species: String? = null,

    @ColumnInfo(name = "type")
    val type: String? = null,

    @ColumnInfo(name = "gender")
    val gender: String? = null,

    @Embedded
    val origin: OriginDbModel? = null,

    @Embedded
    val location: LocationDbModel? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,
) {

    data class OriginDbModel(
        @ColumnInfo(name = "origin_name")
        val name: String? = null
    )

    data class LocationDbModel(
        @ColumnInfo(name = "location_name")
        val name: String? = null
    )
}
