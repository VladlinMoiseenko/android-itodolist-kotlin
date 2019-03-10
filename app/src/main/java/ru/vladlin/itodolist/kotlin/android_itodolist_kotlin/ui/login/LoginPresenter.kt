package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.login

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models.*
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.net.*

class LoginPresenter(var loginView: LoginView?, val loginInteractor: LoginInteractor) :
        LoginInteractor.OnLoginFinishedListener {

    fun validateCredentials(username: String, password: String) {
        viewTasks()
        Log.d("FOFO", "1")
        loginView?.showProgress()
        loginInteractor.login(username, password, this)
    }

    fun onDestroy() {
        loginView = null
    }

    override fun onUsernameError() {
        loginView?.apply {
            setUsernameError()
            hideProgress()
        }
    }

    override fun onPasswordError() {
        loginView?.apply {
            setPasswordError()
            hideProgress()
        }
    }

    override fun onSuccess() {
        //loginView?.navigateToHome()
    }

    fun viewTasks() {
        getObservableViewTasks().subscribeWith<DisposableObserver<TasksModel>>(getObserverViewTasks())
    }

    fun getObservableViewTasks(): Observable<TasksModel> {
        return NetClient.getRetrofit().create(NetInterface::class.java)
                .getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getObserverViewTasks(): DisposableObserver<TasksModel> {
        Log.d("FOFO", "2")
        return object : DisposableObserver<TasksModel>() {

            override fun onNext(@NonNull tasksResponse: TasksModel) {
                //mainView.displayTasks(tasksResponse)
                //Log.d("FOFO", tasksResponse.getData().joinToString {  })
                //Log.d("FOFO","OnNext"+tasksResponse.getTotalResults());
                Log.d("FOFO", tasksResponse.data.toString())
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace();
                Log.d("FOFO", e.toString())
                //mainView.showToast("error_retrieving_data")
            }

            override fun onComplete() {
                //mainView.hideProgress()
            }
        }
    }

}