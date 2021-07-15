package com.example.rickandmorty3.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rickandmorty3.data.model.db.EpisodeDbModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface EpisodeDao {
    @Query("SELECT * FROM episode LIMIT 20 OFFSET (:page - 1) * 20")
    fun getAllEpisode (page: Int): Single<List<EpisodeDbModel>>

    @Insert(entity = EpisodeDbModel::class)
    fun saveEpisode(episode: List<EpisodeDbModel>): Completable
}