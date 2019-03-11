package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.login

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models.*
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.net.*

class LoginPresenter(var loginView: LoginView?, val loginInteractor: LoginInteractor) : LoginInteractor.OnLoginFinishedListener {

    fun validateCredentials(username: String, password: String) {
        if (loginView != null) {
            loginView!!.showProgress()
        }
        loginInteractor.login(username, password, this)
    }

    override fun onUsernameError() {
        if (loginView != null) {
            loginView!!.setUsernameError()
            loginView!!.hideProgress()
        }
    }

    override fun onPasswordError() {
        if (loginView != null) {
            loginView!!.setPasswordError()
            loginView!!.hideProgress()
        }
    }

    override fun onSuccess(username: String, password: String) {
        if (loginView != null) {
            getObservableAuthorize(username, password).subscribeWith<DisposableObserver<AuthorizeModel>>(getObserverAuthorize())
        }
    }

    // -> authorize
    fun getObservableAuthorize(username: String, password: String): Observable<AuthorizeModel> {
        val credentials = Credentials(username, password, null)
        return NetClient.getRetrofit().create(NetInterface::class.java)
                .authorize(credentials)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getObserverAuthorize(): DisposableObserver<AuthorizeModel> {
        return object : DisposableObserver<AuthorizeModel>() {

            override fun onNext(@NonNull response: AuthorizeModel) {
                val authorizationCode = response.data.authorizationCode
                Log.d("FOFO", "authorizationCode " + authorizationCode)
                getToken(authorizationCode)
            }

            override fun onError(@NonNull e: Throwable) {
                loginView!!.hideProgress()
                loginView!!.showToast("error_retrieving_data")
            }

            override fun onComplete() {
                loginView!!.hideProgress()
            }
        }
    }
    // <- authorize

    // -> accesstoken
    private fun getToken(authorizationCode: String) {
        getObservableToken(authorizationCode).subscribeWith<DisposableObserver<AccesstokenModel>>(getObserverToken())
    }

    fun getObservableToken(authorizationCode: String): Observable<AccesstokenModel> {
        val token = Token(authorizationCode)
        return NetClient.getRetrofit().create(NetInterface::class.java)
                .accesstoken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getObserverToken(): DisposableObserver<AccesstokenModel> {
        return object : DisposableObserver<AccesstokenModel>() {
            override fun onNext(@NonNull response: AccesstokenModel) {
                val accessToken = response.data.accessToken
                //Log.d("FOFO", "accessToken " + accessToken)
                loginView!!.saveAccessToken(accessToken)
            }

            override fun onError(@NonNull e: Throwable) {
                //e.printStackTrace();
            }

            override fun onComplete() {
                Log.d("FOFO", "navigateToMain")
                loginView!!.navigateToMain()
            }
        }
    }
    // <- accesstoken

    fun onDestroy() {
        loginView = null
        getObserverAuthorize().dispose()
        getObserverToken().dispose()
    }

}