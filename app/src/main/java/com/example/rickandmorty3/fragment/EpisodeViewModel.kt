package com.example.rickandmorty3.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty3.data.repository.EpisodeRepository
import com.example.rickandmorty3.data.model.api.EpisodeApiModel
import com.example.rickandmorty3.domain.model.EpisodeDomainModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton


class EpisodeViewModel @Inject constructor(
    private val repository: EpisodeRepository
): ViewModel() {

    private var pageNumber = 1
    private var loading = false

    private val pageSubject = BehaviorSubject.create<Int>()
    private val pageFlowable = pageSubject
        .subscribeOn(Schedulers.computation())
        .toFlowable(BackpressureStrategy.BUFFER)
        .share()
    var compositeDisposable = CompositeDisposable()

    var _episode = MutableLiveData<List<EpisodeDomainModel>>()
    val episode: LiveData<List<EpisodeDomainModel>> = _episode

    init {
        pageFlowable
            .onBackpressureDrop()
            .doOnNext { loading = true }
            .concatMapSingle { newPage ->
                repository.getAllEpisode(newPage)
                    .subscribeOn(Schedulers.io())
            }
            .doOnEach { loading = false }
            .subscribeOn((Schedulers.io()))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val newCharacters = _episode.value?.toMutableList() ?: mutableListOf()
                newCharacters.addAll(it)
                _episode.value = newCharacters
            }.apply {
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