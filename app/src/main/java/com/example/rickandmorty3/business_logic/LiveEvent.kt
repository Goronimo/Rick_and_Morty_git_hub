package com.example.rickandmorty3.business_logic

class LiveEvent<T>(private val content: T) {
    // класс "живое мероприятие"
    private var isHandled = false
    // функция: если он уже создавал мероприятие, то ничего
    // иначе создаёт мероприятие и предаёт
    fun getContent(): T? {
        return if (isHandled) {
            null
        } else {
            isHandled = true
            content
        }
    }
}