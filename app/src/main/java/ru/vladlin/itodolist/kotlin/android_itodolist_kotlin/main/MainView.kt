package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.main

interface MainView {
    fun showProgress()
    fun hideProgress()
    fun setItems(items: List<String>)
    fun showMessage(message: String)
}