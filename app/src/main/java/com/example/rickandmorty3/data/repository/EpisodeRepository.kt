package com.example.rickandmorty3.data.repository

import com.example.rickandmorty3.data.local.EpisodeLocalDataSource
import com.example.rickandmorty3.data.model.api.EpisodeApiModel
import com.example.rickandmorty3.data.model.db.EpisodeDbModel
import com.example.rickandmorty3.data.remote.EpisodeRemoteDataSource
import com.example.rickandmorty3.domain.model.EpisodeDomainModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeRepository @Inject constructor(
    private val remoteDataSource: EpisodeRemoteDataSource,
    private val localDataSource: EpisodeLocalDataSource
) {

    fun getAllEpisode(pages: Int): Single<List<EpisodeDomainModel>> {
        return localDataSource.getAllEpisode(pages)
            .map { episode ->
                episode.map { dbModel ->
                    EpisodeDomainModel(
                        id = dbModel.id,
                        name = dbModel.name ?: "no_name",
                        airDate = dbModel.airDate ?: "no_air_date",
                        episode = dbModel.episode ?: "no_episode"
                    )
                }
            }
            .flatMap {
                if (it.isNotEmpty()) {
                    Single.just(it)
                } else {
                    getEpisodeFromRemote(pages)
                }
            }
    }

    private fun getEpisodeFromRemote(pages: Int): Single<List<EpisodeDomainModel>> {
        return remoteDataSource.getEpisode(pages)
            .map { it.results ?: emptyList() }
            .doOnSuccess { storeEpisodeInDb(it) }
            .map { episode ->
                episode.map { apiModel ->
                    EpisodeDomainModel(
                        id = apiModel.id,
                        name = apiModel.name ?: "no_name",
                        airDate = apiModel.airDate ?: "no_air_date",
                        episode = apiModel.episode ?: "no_episode"
                    )
                }
            }
    }

    private fun storeEpisodeInDb(episode: List<EpisodeApiModel>) {
        val dbModel = episode.map {
            EpisodeDbModel(
                id = it.id,
                name = it.name,
                airDate = it.airDate,
                episode = it.episode
            )
        }
        localDataSource.saveEpisode(dbModel)
            .subscribeOn(Schedulers.io())
            .subscribe({}, {
                it.printStackTrace()
            })
    }
}
