package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.main

import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.postDelayed

class FindItemsInteractor {

    fun findItems(callback: (List<String>) -> Unit) {
        postDelayed(2000) { callback(createArrayList()) }
    }

    private fun createArrayList(): List<String> = (1..10).map { "Item $it" }
}