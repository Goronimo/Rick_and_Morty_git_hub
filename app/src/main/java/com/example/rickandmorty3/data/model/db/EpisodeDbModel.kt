package com.example.rickandmorty3.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode")
data class EpisodeDbModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "air date")
    val airDate: String? = null,

    @ColumnInfo(name = "episode")
    val episode: String? = null
)
