package com.example.rickandmorty3.data.local

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty3.data.model.db.CharacterDbModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterLocalDataSource @Inject constructor(
    private val charactersDao: CharactersDao
) {

    fun getAllCharacters(page: Int): Single<List<CharacterDbModel>> {
        return charactersDao.getAllCharacters(page)
    }

    fun saveCharacters(characters: List<CharacterDbModel>): Completable {
        return charactersDao.saveCharacters(characters)
    }
}