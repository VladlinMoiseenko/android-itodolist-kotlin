package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.login

interface LoginView {

    abstract fun showProgress()
    abstract fun hideProgress()
    abstract fun setUsernameError()
    abstract fun setPasswordError()
    abstract fun navigateToMain()
    abstract fun navigateToRegistration()
    abstract fun saveAccessToken(accessToken: String)
    abstract fun showToast(key: String)

}