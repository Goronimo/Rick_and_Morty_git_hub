package com.example.rickandmorty3.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty3.fragment.CharactersViewModel
import com.example.rickandmorty3.fragment.EpisodeViewModel
import com.example.rickandmorty3.fragment.LocationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.assisttech.assistmpos.di.annotation.ViewModelKey

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(
        factory: UniversalViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    abstract fun provideCharacters(vm: CharactersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun provideLocations(vm: LocationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeViewModel::class)
    abstract fun provideEpisode(vm: EpisodeViewModel): ViewModel
}

// шаблонный код для даггера
// с добавлением моделей которые нужны
