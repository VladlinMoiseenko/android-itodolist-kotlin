package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin

import android.os.Handler

fun postDelayed(delayMillis: Long, task: () -> Unit) {
    Handler().postDelayed(task, delayMillis)
}