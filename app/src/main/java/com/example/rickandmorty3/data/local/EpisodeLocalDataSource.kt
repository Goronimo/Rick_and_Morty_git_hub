package com.example.rickandmorty3.data.local

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty3.data.model.db.EpisodeDbModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeLocalDataSource @Inject constructor() {

    private lateinit var database: RickAndMortyDatabase

    private val episodeDao by lazy { database.episodeDao() }
//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
    fun init(context: Context){
        database = Room.databaseBuilder(
            context.applicationContext,
            RickAndMortyDatabase::class.java,
            "rick_and_morty_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getAllEpisode(page:Int): Single<List<EpisodeDbModel>> {
        return  episodeDao.getAllEpisode(page)
    }

    fun saveEpisode (episode: List<EpisodeDbModel>): Completable {
        return  episodeDao.saveEpisode(episode)
    }
}