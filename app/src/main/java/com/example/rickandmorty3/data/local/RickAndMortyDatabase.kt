package com.example.rickandmorty3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmorty3.data.model.db.CharacterDbModel
import com.example.rickandmorty3.data.model.db.EpisodeDbModel
import com.example.rickandmorty3.data.model.db.LocationDbModel

@Database(entities = [CharacterDbModel::class, LocationDbModel::class, EpisodeDbModel::class], version = 3)
abstract class RickAndMortyDatabase: RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
    abstract fun locationDao(): LocationDao
    abstract fun episodeDao(): EpisodeDao
}