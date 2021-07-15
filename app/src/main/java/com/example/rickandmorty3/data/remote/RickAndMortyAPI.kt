package com.example.rickandmorty3.data.remote

import com.example.rickandmorty3.data.model.api.CharactersListApiModel
import com.example.rickandmorty3.data.model.api.EpisodeListApiModel
import com.example.rickandmorty3.data.model.api.LocationListApiModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyAPI {
    @GET("character/")
    fun getCharactersByPage(@Query("page") pages: Int): Single<CharactersListApiModel>

    @GET("location/")
    fun getAllLocation(@Query("page") pages: Int): Single<LocationListApiModel>

    @GET("episode/")
    fun getAllEpisode(@Query("page") pages: Int): Single<EpisodeListApiModel>
}