package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.R
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models.*
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.net.*
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.main.MainActivity

class LoginActivity : AppCompatActivity(), LoginView {

    private val presenter = LoginPresenter(this, LoginInteractor())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button.setOnClickListener { validateCredentials() }
    }

    private fun validateCredentials() {
        presenter.validateCredentials(username.text.toString(), password.text.toString())
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun setUsernameError() {
        username.error = getString(R.string.username_error)
    }

    override fun setPasswordError() {
        password.error = getString(R.string.password_error)
    }

    override fun navigateToHome() {
        //startActivity(Intent(this, MainActivity::class.java))
        //viewTasks()
    }

//    fun viewTasks() {
//        getObservableViewTasks().subscribeWith<DisposableObserver<TasksModel>>(getObserverViewTasks())
//    }
//
//    fun getObservableViewTasks(): Observable<TasksModel> {
//        return NetClient.getRetrofit().create(NetInterface::class.java)
//                .getTasks()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//    }
//
//    fun getObserverViewTasks(): DisposableObserver<TasksModel> {
//        return object : DisposableObserver<TasksModel>() {
//
//            override fun onNext(@NonNull tasksResponse: TasksModel) {
//                //mainView.displayTasks(tasksResponse)
//            }
//
//            override fun onError(@NonNull e: Throwable) {
//                e.printStackTrace();
//                //mainView.showToast("error_retrieving_data")
//            }
//
//            override fun onComplete() {
//                //mainView.hideProgress()
//            }
//        }
//    }

}