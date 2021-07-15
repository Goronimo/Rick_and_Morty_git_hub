package com.example.rickandmorty3.data.remote

import com.example.rickandmorty3.data.model.api.LocationListApiModel
import io.reactivex.Single
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRemoteDataSource @Inject constructor(
    private val api: RickAndMortyAPI
) {

    fun getLocation(pages: Int): Single<LocationListApiModel> {
        return api.getAllLocation(
            pages = pages
        )
    }
}