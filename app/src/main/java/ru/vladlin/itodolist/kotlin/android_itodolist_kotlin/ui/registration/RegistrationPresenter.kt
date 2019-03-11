package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.registration

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models.*
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.net.*

class RegistrationPresenter (
        private var registrationView: RegistrationView?,
        private val registrationInteractor: RegistrationInteractor) : RegistrationInteractor.OnLoginFinishedListener {
        private var credentials: Credentials? = null

    fun validateCredentials(username: String, password: String, email: String) {
        if (registrationView != null) {
            registrationView!!.showProgress()
        }
        this.credentials = Credentials(username, password, email)
        registrationInteractor.login(username, password, email, this)
    }

    override fun onUsernameError() {
        if (registrationView != null) {
            registrationView!!.setUsernameError()
            registrationView!!.hideProgress()
        }
    }

    override fun onPasswordError() {
        if (registrationView != null) {
            registrationView!!.setPasswordError()
            registrationView!!.hideProgress()
        }
    }

    override fun onEmailError() {
        if (registrationView != null) {
            registrationView!!.setEmailError()
            registrationView!!.hideProgress()
        }
    }

    override fun onSuccess() {
        if (registrationView != null) {
            registration()
        }
    }

    // -> register
    private fun registration() {
        getObservableRegistration().subscribeWith<DisposableObserver<ProfileModel>>(getObserverRegistration())
    }
    fun getObservableRegistration(): Observable<ProfileModel> {
        return NetClient.getRetrofit().create(NetInterface::class.java)
                .register(this.credentials!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
    fun getObserverRegistration(): DisposableObserver<ProfileModel> {
        return object : DisposableObserver<ProfileModel>() {
            override fun onNext(@NonNull response: ProfileModel) {}
            override fun onError(@NonNull e: Throwable) {
                registrationView!!.hideProgress()
                registrationView!!.showToast("error_retrieving_data")
            }

            override fun onComplete() {
                registrationView!!.hideProgress()
                authorize()
            }
        }
    }
    // <- register

    // -> authorize
    private fun authorize() {
        getObservableAuthorize().subscribeWith(getObserverAuthorize())
    }
    fun getObservableAuthorize(): Observable<AuthorizeModel> {
        return NetClient.getRetrofit().create(NetInterface::class.java)
                .authorize(this.credentials!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
    fun getObserverAuthorize(): DisposableObserver<AuthorizeModel> {
        return object : DisposableObserver<AuthorizeModel>() {
            override fun onNext(@NonNull response: AuthorizeModel) {
                val authorizationCode = response.data.authorizationCode
                getToken(authorizationCode)
            }
            override fun onError(@NonNull e: Throwable) {
                registrationView!!.hideProgress()
                registrationView!!.showToast("error_retrieving_data")
            }
            override fun onComplete() {
                registrationView!!.hideProgress()
            }
        }
    }
    // <- authorize

    // -> accesstoken
    private fun getToken(authorizationCode: String) {
        getObservableToken(authorizationCode).subscribeWith(getObserverToken())
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
                registrationView!!.saveAccessToken(accessToken)
            }
            override fun onError(@NonNull e: Throwable) {
                //e.printStackTrace();
            }
            override fun onComplete() {
                registrationView!!.hideProgress()
                registrationView!!.navigateToMain()
            }
        }
    }
    // <- accesstoken

    fun onDestroy() {
        registrationView = null
        getObserverRegistration().dispose()
        getObserverAuthorize().dispose()
        getObserverToken().dispose()
    }
}