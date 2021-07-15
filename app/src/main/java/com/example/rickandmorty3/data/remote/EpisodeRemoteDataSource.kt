package com.example.rickandmorty3.data.remote

import com.example.rickandmorty3.data.model.api.EpisodeListApiModel
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeRemoteDataSource @Inject constructor(
    private val api: RickAndMortyAPI
) {

    fun getEpisode(pages: Int): Single<EpisodeListApiModel> {
        return api.getAllEpisode(
            pages = pages
        )
    }
}