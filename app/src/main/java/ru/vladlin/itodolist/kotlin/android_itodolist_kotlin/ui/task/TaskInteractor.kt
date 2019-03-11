package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.task

import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.postDelayed

class TaskInteractor {
    interface OnTaskFinishedListener {
        fun onTitledError()
        fun onSuccess(task_title: String, task_content: String)
    }

    fun save(task_title: String, task_content: String, listener: OnTaskFinishedListener) {
        postDelayed(1000) {
            when {
                task_title.isEmpty() -> listener.onTitledError()
                else -> listener.onSuccess(task_title, task_content)
            }
        }
    }
}