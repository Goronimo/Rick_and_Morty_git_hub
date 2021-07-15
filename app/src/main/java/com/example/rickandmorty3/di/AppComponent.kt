package com.example.rickandmorty3.di

import android.app.Application
import com.example.rickandmorty3.MainActivity
import com.example.rickandmorty3.fragment.CharactersFragment
import com.example.rickandmorty3.fragment.EpisodeFragment
import com.example.rickandmorty3.fragment.LocationFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(fragment: CharactersFragment)
    fun inject(fragment: LocationFragment)
    fun inject(fragment: EpisodeFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }
}