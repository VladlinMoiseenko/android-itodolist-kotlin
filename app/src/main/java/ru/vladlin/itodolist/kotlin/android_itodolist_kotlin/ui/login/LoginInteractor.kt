package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.login

import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.postDelayed

class LoginInteractor {

    interface OnLoginFinishedListener {
        fun onUsernameError()
        fun onPasswordError()
        fun onSuccess(username: String, password: String)
    }

    fun login(username: String, password: String, listener: OnLoginFinishedListener) {
        postDelayed(2000) {
            when {
                username.isEmpty() -> listener.onUsernameError()
                password.isEmpty() -> listener.onPasswordError()
                else -> listener.onSuccess(username, password)
            }
        }
    }
}