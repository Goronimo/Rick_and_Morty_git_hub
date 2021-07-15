package com.example.rickandmorty3.data.local

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty3.data.model.db.LocationDbModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationLocalDataSource @Inject constructor(
    private val locationDao: LocationDao
){

    fun getAllLocation(page: Int): Single<List<LocationDbModel>> {
        return locationDao.getAllLocation(page)
    }

    fun saveLocation(location: List<LocationDbModel>): Completable {
        return locationDao.saveLocation(location)
    }
}