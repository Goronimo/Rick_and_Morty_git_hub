package com.example.rickandmorty3.data.remote

import com.example.rickandmorty3.data.model.api.CharactersListApiModel
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRemoteDataSource @Inject constructor(
    private val api: RickAndMortyAPI
) {

    fun getCharacters(pages: Int): Single<CharactersListApiModel> {
        return api.getCharactersByPage(
            pages = pages
        )
    }
}