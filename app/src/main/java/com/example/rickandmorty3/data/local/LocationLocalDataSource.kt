package com.example.rickandmorty3.data.local

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty3.data.model.db.LocationDbModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationLocalDataSource @Inject constructor(){

    private lateinit var database: RickAndMortyDatabase

    private val locationDao by lazy { database.locationDao() }

    fun init(context: Context){
        database = Room.databaseBuilder(
            context.applicationContext,
            RickAndMortyDatabase::class.java,
            "rick_and_morty_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getAllLocation(page: Int): Single<List<LocationDbModel>> {
        return locationDao.getAllLocation(page)
    }

    fun saveLocation(location: List<LocationDbModel>): Completable {
        return locationDao.saveLocation(location)
    }
}