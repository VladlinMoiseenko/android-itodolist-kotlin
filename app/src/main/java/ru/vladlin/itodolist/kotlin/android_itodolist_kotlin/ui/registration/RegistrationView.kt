package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.registration

interface RegistrationView {
    abstract fun showProgress()
    abstract fun hideProgress()
    abstract fun setUsernameError()
    abstract fun setPasswordError()
    abstract fun setEmailError()
    abstract fun navigateToMain()
    abstract fun saveAccessToken(accessToken: String)
    abstract fun showToast(key: String)
}