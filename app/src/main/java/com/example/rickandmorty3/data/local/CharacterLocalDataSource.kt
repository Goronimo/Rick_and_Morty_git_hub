package com.example.rickandmorty3.data.local

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty3.data.model.db.CharacterDbModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterLocalDataSource @Inject constructor() {

    private lateinit var database: RickAndMortyDatabase

    private val charactersDao by lazy { database.charactersDao() }

    fun init(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            RickAndMortyDatabase::class.java,
            "rick_and_morty_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getAllCharacters(page: Int): Single<List<CharacterDbModel>> {
        return charactersDao.getAllCharacters(page)
    }

    fun saveCharacters(characters: List<CharacterDbModel>): Completable {
        return charactersDao.saveCharacters(characters)
    }
}