package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.main

import android.util.Log
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
        Log.d("FOFO", "accessToken1 " + accessToken)
        return NetClient.getRetrofit().create(NetInterface::class.java)
                .getTasks(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
    fun getObserverViewTasks(): DisposableObserver<TasksModel> {
        return object : DisposableObserver<TasksModel>() {

            override fun onNext(@NonNull tasksResponse: TasksModel) {
                Log.d("FOFO", "tasksResponse status " + tasksResponse.status)
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

    // ->
    // <-
    fun logout() {
        if (mainView != null) {
            mainView!!.showProgress()
        }
        //observableLogout.subscribeWith<DisposableObserver<LogoutModel>>(observerLogout)
    }


    // ->
    // <-
    fun deleteTask(taskId: String) {
        //getObservableDelete(taskId).subscribeWith<DisposableObserver<Task>>(observerDelete)
    }

//    fun getObservableDelete(taskId: String): Observable<Task> {
//        val accessToken = mainView!!.getAccessToken()
//        return NetClient.getRetrofit().create(NetInterface::class.java)
//                .deleteTask(taskId, accessToken)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//    }
//


//    val observableLogout: Observable<LogoutModel>
//        get() {
//            val accessToken = mainView!!.getAccessToken()
//            return NetClient.getRetrofit().create(NetInterface::class.java)
//                    .logout(accessToken)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//        }
//
//    //mainView.displayTasks(tasksResponse);
//    //e.printStackTrace();
//    val observerLogout: DisposableObserver<LogoutModel>
//        get() = object : DisposableObserver<LogoutModel>() {
//
//            override fun onNext(@NonNull logoutResponse: LogoutModel) {}
//
//            override fun onError(@NonNull e: Throwable) {
//                mainView!!.showToast("error_retrieving_data")
//            }
//
//            override fun onComplete() {
//                mainView!!.hideProgress()
//            }
//        }
//
//
//    //mainView.show(String.format("%s Задача удалена", taskResponse.getStatus()));
//    //e.printStackTrace();
//    val observerDelete: DisposableObserver<Task>
//        get() = object : DisposableObserver<Task>() {
//
//            override fun onNext(@NonNull taskResponse: Task) {
//                mainView!!.showToast("task_deleted")
//            }
//
//            override fun onError(@NonNull e: Throwable) {
//                mainView!!.showToast("error_retrieving_data")
//            }
//
//            override fun onComplete() {
//                viewTasks()
//            }
//        }
//
//


    fun onDestroy() {
        mainView = null
        getObserverViewTasks().dispose()
//        observerLogout.dispose()
//        observerDelete.dispose()
    }

}