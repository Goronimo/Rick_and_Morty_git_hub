package com.example.rickandmorty3.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty3.data.repository.LocationRepository
import com.example.rickandmorty3.data.model.api.LocationApiModel
import com.example.rickandmorty3.domain.model.LocationDomainModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

class LocationViewModel @Inject constructor(
    private val repository: LocationRepository
): ViewModel() {

    private var pageNumber = 1
    private var loading = false

    private val pageSubject = BehaviorSubject.create<Int>()
    private val pageFlowable = pageSubject
        .subscribeOn(Schedulers.computation())
        .toFlowable(BackpressureStrategy.BUFFER)
        .share()
    var compositeDisposable = CompositeDisposable()

    private val _location = MutableLiveData<List<LocationDomainModel>>()
    val location: LiveData<List<LocationDomainModel>> = _location

    init {
        pageFlowable
            .onBackpressureDrop()
            .doOnNext { loading = true }
            .concatMapSingle { newPage ->
                repository.getAllLocation(newPage)
                    .subscribeOn(Schedulers.io())
            }
            .doOnEach { loading = false }
            .subscribeOn((Schedulers.io()))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                val newCharacters = _location.value?.toMutableList() ?: mutableListOf()
                newCharacters.addAll(it)
                _location.value = newCharacters
            }, { throwable ->
                Log.d("error", "no more location", throwable)
            }).apply {
                compositeDisposable.add(this)
            }
        pageSubject.onNext(1)
    }

    fun onNewPageRequested() {
        if (loading) {
            return
        }
        pageNumber++
        pageSubject.onNext(pageNumber)
        loading = true
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}