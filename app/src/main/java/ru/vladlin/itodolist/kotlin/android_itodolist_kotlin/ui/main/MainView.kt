package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.main

import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models.*

interface MainView {
    abstract fun showProgress()
    abstract fun hideProgress()
    abstract fun showToast(key: String)
    abstract fun displayTasks(tasksResponse: TasksModel)
    abstract fun getAccessToken(): String
}