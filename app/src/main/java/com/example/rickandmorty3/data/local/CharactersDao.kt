package com.example.rickandmorty3.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rickandmorty3.data.model.db.CharacterDbModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CharactersDao {
    @Query("SELECT * FROM characters LIMIT 20 OFFSET (:page - 1) * 20")
    fun getAllCharacters(page: Int): Single<List<CharacterDbModel>>

    // задатки на настройки search
    @Query("SELECT * FROM characters WHERE status is 'Dead'")
    fun getAllDead(): Single<List<CharacterDbModel>>

    @Insert(entity = CharacterDbModel::class)
    fun saveCharacters(characters: List<CharacterDbModel>): Completable

}
