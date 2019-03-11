package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.task

interface TaskView {
    abstract fun showProgress()
    abstract fun hideProgress()
    abstract fun setTitleError()
    abstract fun showToast(key: String)
    abstract fun navigateToMain()
    abstract fun getAccessToken(): String
    abstract fun getIdTask(): String
}