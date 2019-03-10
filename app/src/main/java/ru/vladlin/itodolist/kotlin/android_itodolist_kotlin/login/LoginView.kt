package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.login

interface LoginView {
    fun showProgress()
    fun hideProgress()
    fun setUsernameError()
    fun setPasswordError()
    fun navigateToHome()
}