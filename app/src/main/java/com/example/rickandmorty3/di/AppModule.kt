package com.example.rickandmorty3.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.rickandmorty3.data.local.*
import com.example.rickandmorty3.data.remote.CharacterRemoteDataSource
import com.example.rickandmorty3.data.remote.RickAndMortyAPI
import com.example.rickandmorty3.data.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Application): RickAndMortyDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            RickAndMortyDatabase::class.java,
            "rick_and_morty_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCharactersDao(database: RickAndMortyDatabase) : CharactersDao {
        return database.charactersDao()
    }

    @Provides
    @Singleton
    fun provideLocationDao(database: RickAndMortyDatabase) : LocationDao {
        return database.locationDao()
    }

    @Provides
    @Singleton
    fun provideEpisodeDao(database: RickAndMortyDatabase) : EpisodeDao {
        return database.episodeDao()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyApi(
        client: OkHttpClient,
    ): RickAndMortyAPI {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RickAndMortyAPI::class.java)
    }

// нужно если не провайдишь на прямую
    // для более сложных конструкций

//    @Provides
//    @Singleton
//    fun provideCharacterRepository(
//        remote: CharacterRemoteDataSource,
//        local: CharacterLocalDataSource,
//    ): CharacterRepository {
//        return CharacterRepository(
//            remoteDataSource = remote,
//            localDataSource = local
//        )
//    }
}