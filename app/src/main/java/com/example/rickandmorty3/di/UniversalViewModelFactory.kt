package com.example.rickandmorty3.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * [ViewModelProvider.Factory] to instantiate all [ViewModel]s using a map with relevant [Provider].
 */
@Singleton
class UniversalViewModelFactory
@Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val vmProvider = viewModels[modelClass]
        return vmProvider?.get() as T?
            ?: throw IllegalArgumentException("Unknown ViewModel class ${modelClass.canonicalName}")
    }
}

// шаблонный код для даггера
