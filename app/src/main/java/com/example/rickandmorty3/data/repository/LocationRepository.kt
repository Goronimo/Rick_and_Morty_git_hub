package com.example.rickandmorty3.data.repository

import com.example.rickandmorty3.data.local.LocationLocalDataSource
import com.example.rickandmorty3.data.model.api.LocationApiModel
import com.example.rickandmorty3.data.model.db.LocationDbModel
import com.example.rickandmorty3.data.remote.LocationRemoteDataSource
import com.example.rickandmorty3.domain.model.LocationDomainModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val remoteDataSource: LocationRemoteDataSource,
    private val localDataSource: LocationLocalDataSource
) {

    fun getAllLocation(pages: Int): Single<List<LocationDomainModel>> {
        return localDataSource.getAllLocation(pages)
            .map { location ->
                location.map { dbModel ->
                    LocationDomainModel(
                        id = dbModel.id,
                        name = dbModel.name ?: "no_name",
                        type = dbModel.type ?: "no_type",
                        dimension = dbModel.dimension ?: "no_dimension",
                        url = dbModel.url ?: "no_url"
                    )
                }
            }
            .flatMap {
                if (it.isNotEmpty()) {
                    Single.just(it)
                } else {
                    getLocationFromRemote(pages)
                }
            }
    }

    private fun getLocationFromRemote(page: Int): Single<List<LocationDomainModel>> {
        return remoteDataSource.getLocation(page)
            .map { it.results ?: emptyList() }
            .doOnSuccess { storeLocationInDb(it) }
            .map { location ->
                location.map { apiModel ->
                    LocationDomainModel(
                        id = apiModel.id,
                        name = apiModel.name ?: "no_name",
                        type = apiModel.type ?: "no_type",
                        dimension = apiModel.dimension ?: "no_dimension",
                        url = apiModel.url ?: "no_url"
                    )
                }
            }
    }

    private fun storeLocationInDb(location: List<LocationApiModel>) {
        val dbModel = location.map {
            LocationDbModel(
                id = it.id,
                name = it.name,
                type = it.type,
                dimension = it.dimension,
                url = it.url
            )
        }
        localDataSource.saveLocation(dbModel)
            .subscribeOn(Schedulers.io())
            .subscribe({}, {
                it.printStackTrace()
            })
    }
}