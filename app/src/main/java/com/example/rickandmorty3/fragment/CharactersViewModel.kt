package com.example.rickandmorty3.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.rickandmorty3.business_logic.LiveEvent
import com.example.rickandmorty3.data.repository.CharacterRepository
import com.example.rickandmorty3.domain.model.CharacterDomainModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
) : ViewModel() {

    // параметры
    // пагинация страниц
    private var pageNumber = 1

    // подзагрузка следующих страниц
    private var loading = false

    // RXJava
    // наблюдатель за последующими добавленными элементами
    private val pageSubject = BehaviorSubject.create<Int>()

    // подписываемся
    private val pageFlowable = pageSubject
        .subscribeOn(Schedulers.computation())
        .toFlowable(BackpressureStrategy.BUFFER)
        .share()

    //  храним в нем все расходные материалы
    var compositeDisposable = CompositeDisposable()

    private val _navigation = MutableLiveData<LiveEvent<NavDirections>>()
    val navigation: LiveData<LiveEvent<NavDirections>> = _navigation

    private val _selectedHero = MutableLiveData<CharacterDomainModel>()
    val selectedHero: LiveData<CharacterDomainModel> = _selectedHero

    // для внутреннего пользования (изменяемый)
    var _characters = MutableLiveData<List<CharacterDomainModel>>()

    // для внешнего пользования (не изменяемый)
    val characters: LiveData<List<CharacterDomainModel>> = _characters

    init {
        pageFlowable
            .onBackpressureDrop()
            .doOnNext { loading = true }
            .concatMapSingle { newPage ->
                characterRepository.getAllCharacters(newPage)
                    .subscribeOn(Schedulers.io())
            }
            .doOnEach { loading = false }
            .subscribeOn((Schedulers.io()))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val newCharacters = _characters.value?.toMutableList() ?: mutableListOf()
                newCharacters.addAll(it)
                _characters.value = newCharacters
            }, { throwable ->
                Log.d("error", "no more characters", throwable)
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

    fun onCardClicked(position: Int) {
        // в liveData сохраням персонажа из списка по позиции
        _selectedHero.value = characters.value?.get(position)
        // LiveEvent получает (созданные action для nav_grath за кулисами)
        _navigation.value = LiveEvent(CharactersFragmentDirections.actionListToInfo())
    }
}