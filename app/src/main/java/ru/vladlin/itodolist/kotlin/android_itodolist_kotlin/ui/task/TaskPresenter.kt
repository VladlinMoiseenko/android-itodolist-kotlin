package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.task

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models.*
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.net.*

class TaskPresenter (private var taskView: TaskView?, private val taskInteractor: TaskInteractor) : TaskInteractor.OnTaskFinishedListener {

    fun validateTask(task_title: String, task_content: String) {
        if (taskView != null) {
            taskView!!.showProgress()
        }
        taskInteractor.save(task_title, task_content, this)
    }

    override fun onTitledError() {
        if (taskView != null) {
            taskView!!.setTitleError()
            taskView!!.hideProgress()
        }
    }

    override fun onSuccess(task_title: String, task_content: String) {
        if (taskView != null) {
            if (taskView!!.getIdTask() == "") {
                getObservableSave(task_title, task_content).subscribeWith<DisposableObserver<Task>>(getObserverSave())
            } else {
                getObservableUpdate(taskView!!.getIdTask(), task_title, task_content).subscribeWith(getObserverSave())
            }
        }
    }

    fun getObservableSave(title: String, content: String): Observable<Task> {
        val taskModel = TaskModel(title, content)
        val mAccessToken = taskView!!.getAccessToken()
        return NetClient.getRetrofit().create(NetInterface::class.java)
                .createTask(taskModel, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getObservableUpdate(taskId: String, title: String, content: String): Observable<Task> {
        val taskModel = TaskModel(title, content)
        val accessToken = taskView!!.getAccessToken()
        return NetClient.getRetrofit().create(NetInterface::class.java)
                .updateTask(taskId, taskModel, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getObserverSave(): DisposableObserver<Task> {
        return object : DisposableObserver<Task>() {
            override fun onNext(@NonNull response: Task) {}
            override fun onError(@NonNull e: Throwable) {
                taskView!!.hideProgress()
                taskView!!.showToast("error_retrieving_data")
            }
            override fun onComplete() {
                taskView!!.navigateToMain()
            }
        }
    }

    fun onDestroy() {
        taskView = null
        getObserverSave().dispose()
    }

}