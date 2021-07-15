package com.example.rickandmorty3.data.local

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty3.data.model.db.EpisodeDbModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeLocalDataSource @Inject constructor(
    private val episodeDao: EpisodeDao
) {

    fun getAllEpisode(page:Int): Single<List<EpisodeDbModel>> {
        return  episodeDao.getAllEpisode(page)
    }

    fun saveEpisode (episode: List<EpisodeDbModel>): Completable {
        return  episodeDao.saveEpisode(episode)
    }
}