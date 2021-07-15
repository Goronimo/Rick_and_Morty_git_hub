package com.example.rickandmorty3.data.repository

import com.example.rickandmorty3.data.local.CharacterLocalDataSource
import com.example.rickandmorty3.data.model.api.CharacterApiModel
import com.example.rickandmorty3.data.model.db.CharacterDbModel
import com.example.rickandmorty3.data.remote.CharacterRemoteDataSource
import com.example.rickandmorty3.domain.model.CharacterDomainModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterLocalDataSource
) {

    // отдаёт во viewModel фрагмента
    fun getAllCharacters(pages: Int): Single<List<CharacterDomainModel>> {
        return localDataSource.getAllCharacters(pages)
            .map { characters ->
                characters.map { dbModel ->
                    val origin = CharacterDomainModel.OriginDomainModel(
                        dbModel.origin?.name ?: "no"
                    )
                    val location = CharacterDomainModel.LocationDomainModel(
                        dbModel.location?.name ?: "no"
                    )

                    CharacterDomainModel(
                        id = dbModel.id,
                        name = dbModel.name ?: "no_name",
                        status = dbModel.status ?: "no_status",
                        species = dbModel.species ?: "no_species",
                        type = dbModel.type ?: "no_type",
                        gender = dbModel.gender ?: "no_gender",
                        origin = origin,
                        location = location,
                        image = dbModel.image ?: "no_image"
                    )
                }
            }
            .flatMap {
                if (it.isNotEmpty()) {
                    Single.just(it)
                } else {
                    getCharactersFromRemote(pages)
                }
            }
    }

    // получает список персонажей от сервера? маппит их на CharacterDomainModel
    private fun getCharactersFromRemote(pages: Int): Single<List<CharacterDomainModel>> {
        return remoteDataSource.getCharacters(pages)
            .map { it.results ?: emptyList() }
            .doOnSuccess { storeCharactersInDb(it) }
            .map { characters ->
                characters.map { apiModel ->
                    val origin = CharacterDomainModel.OriginDomainModel(
                        apiModel.origin?.name ?: "no"
                    )
                    val location = CharacterDomainModel.LocationDomainModel(
                        apiModel.location?.name ?: "no"
                    )
                    CharacterDomainModel(
                        id = apiModel.id,
                        name = apiModel.name ?: "no_name",
                        status = apiModel.status ?: "no_status",
                        species = apiModel.species ?: "no_species",
                        type = apiModel.type ?: "no_type",
                        gender = apiModel.gender ?: "no_gender",
                        origin = origin,
                        location = location,
                        image = apiModel.image ?: "no_image"
                    )
                }
            }
    }

    private fun storeCharactersInDb(characters: List<CharacterApiModel>) {
        val dbModels = characters.map {
            val origin = it.origin?.let { CharacterDbModel.OriginDbModel(it.name) }
            val location = it.location?.let { CharacterDbModel.LocationDbModel(it.name) }
            CharacterDbModel(
                id = it.id,
                name = it.name,
                status = it.status,
                species = it.species,
                type = it.type,
                gender = it.gender,
                origin = origin,
                location = location,
                image = it.image
            )
        }

        localDataSource.saveCharacters(dbModels)
            .subscribeOn(Schedulers.io())
            .subscribe({
            }, {
                it.printStackTrace()
            })
    }
}