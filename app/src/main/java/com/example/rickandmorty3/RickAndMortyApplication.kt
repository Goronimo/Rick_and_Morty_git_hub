package com.example.rickandmorty3

import android.app.Application
import com.example.rickandmorty3.data.local.CharacterLocalDataSource
import com.example.rickandmorty3.data.local.EpisodeLocalDataSource
import com.example.rickandmorty3.data.local.LocationLocalDataSource
import com.example.rickandmorty3.di.AppComponent
import com.example.rickandmorty3.di.DaggerAppComponent

class RickAndMortyApplication: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder().application(this).build()
        super.onCreate()

//        LocationLocalDataSource.init(this)
//        EpisodeLocalDataSource.init(this)
    }
}