package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.login

import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.postDelayed

//class LoginInteractor {
//
//    interface OnLoginFinishedListener {
//        fun onUsernameError()
//        fun onPasswordError()
//        fun onSuccess()
//    }
//
//    fun login(username: String, password: String, listener: OnLoginFinishedListener) {
//        // Mock login. I'm creating a handler to delay the answer a couple of seconds
//        postDelayed(2000) {
//            when {
//                username.isEmpty() -> listener.onUsernameError()
//                password.isEmpty() -> listener.onPasswordError()
//                else -> listener.onSuccess()
//            }
//        }
//    }
//}

class LoginInteractor {

    interface OnLoginFinishedListener {
        fun onUsernameError()
        fun onPasswordError()
        fun onSuccess(username: String, password: String)
    }

    fun login(username: String, password: String, listener: OnLoginFinishedListener) {
//        Handler().postDelayed({
//            if (TextUtils.isEmpty(username)) {
//                listener.onUsernameError()
//                return@new Handler().postDelayed
//            }
//            if (TextUtils.isEmpty(password)) {
//                listener.onPasswordError()
//                return@new Handler().postDelayed
//            }
//            listener.onSuccess(username, password)
//        }, 500)
        postDelayed(1000) {
            when {
                username.isEmpty() -> listener.onUsernameError()
                password.isEmpty() -> listener.onPasswordError()
                else -> listener.onSuccess(username, password)
            }
        }
    }
}