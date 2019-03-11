package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.registration

import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.postDelayed

class RegistrationInteractor {

    interface OnLoginFinishedListener {
        fun onUsernameError()
        fun onPasswordError()
        fun onEmailError()
        fun onSuccess()
    }

    fun login(username: String, password: String, email: String, listener: OnLoginFinishedListener) {
        postDelayed(1000) {
            when {
                username.isEmpty() -> listener.onUsernameError()
                password.isEmpty() -> listener.onPasswordError()
                email.isEmpty() -> listener.onEmailError()
                else -> listener.onSuccess()
            }
        }

    }
}