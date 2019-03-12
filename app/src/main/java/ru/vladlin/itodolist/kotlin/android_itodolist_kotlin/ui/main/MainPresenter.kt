package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.main

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models.*
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.net.*

class MainPresenter(private var mainView: MainView?) {

    fun onResume() {
        if (mainView != null) {
            mainView!!.showProgress()
        }
        viewTasks()
    }

    // -> getTasks
    fun viewTasks() {
        getObservableViewTasks().subscribeWith<DisposableObserver<TasksModel>>(getObserverViewTasks())
    }
    fun getObservableViewTasks(): Observable<TasksModel> {
        val accessToken = mainView!!.getAccessToken()
        return NetClient.getRetrofit().create(NetInterface::class.java)
                .getTasks(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
    fun getObserverViewTasks(): DisposableObserver<TasksModel> {
        return object : DisposableObserver<TasksModel>() {
            override fun onNext(@NonNull tasksResponse: TasksModel) {
                mainView!!.displayTasks(tasksResponse)
            }
            override fun onError(@NonNull e: Throwable) {
                mainView!!.showToast("error_retrieving_data")
            }
            override fun onComplete() {
                mainView!!.hideProgress()
            }
        }
    }
    // <- getTasks

    // -> logout
    fun logout() {
        if (mainView != null) {
            mainView!!.showProgress()
        }
        getObservableLogout().subscribeWith(getObserverLogout())
    }
    fun getObservableLogout(): Observable<LogoutModel> {
        val accessToken = mainView!!.getAccessToken()
        return NetClient.getRetrofit().create(NetInterface::class.java)
                .logout(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
    fun getObserverLogout(): DisposableObserver<LogoutModel> {
        return object : DisposableObserver<LogoutModel>() {
            override fun onNext(@NonNull logoutResponse: LogoutModel) {
            }
            override fun onError(@NonNull e: Throwable) {
                mainView!!.showToast("error_retrieving_data")
            }
            override fun onComplete() {
                mainView!!.hideProgress()
            }
        }
    }
    // <- logout

    // -> deleteTask
    fun deleteTask(taskId: String) {
        getObservableDelete(taskId).subscribeWith(getObserverDelete())
    }
    fun getObservableDelete(taskId: String): Observable<Task> {
        val accessToken = mainView!!.getAccessToken()
        return NetClient.getRetrofit().create(NetInterface::class.java)
                .deleteTask(taskId, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
    fun getObserverDelete(): DisposableObserver<Task> {
        return object : DisposableObserver<Task>() {
            override fun onNext(@NonNull taskResponse: Task) {
                mainView!!.showToast("task_deleted")
            }
            override fun onError(@NonNull e: Throwable) {
                mainView!!.showToast("error_retrieving_data")
            }
            override fun onComplete() {
                viewTasks()
            }
        }
    }
    // <- deleteTask

    fun onDestroy() {
        mainView = null
        getObserverViewTasks().dispose()
        getObserverLogout().dispose()
        getObserverDelete().dispose()
    }

}