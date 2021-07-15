package com.example.rickandmorty3.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rickandmorty3.data.model.db.LocationDbModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LocationDao {
    @Query("SElECT * FROM location LIMIT 20 OFFSET (:page -1) * 20")
    fun getAllLocation(page: Int): Single<List<LocationDbModel>>

    @Insert(entity = LocationDbModel::class)
    fun saveLocation(location: List<LocationDbModel>): Completable
}