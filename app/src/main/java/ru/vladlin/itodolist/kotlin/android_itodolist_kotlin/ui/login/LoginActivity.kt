package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.R
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.main.MainActivity
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.registration.RegistrationActivity

class LoginActivity : AppCompatActivity(), LoginView {

    private var progressBar: ProgressBar? = null
    private var username: EditText? = null
    private var password: EditText? = null
    private var presenter: LoginPresenter? = null
    private var mSettings: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar = findViewById(R.id.progress)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)

        findViewById<View>(R.id.btn_login).setOnClickListener { v -> validateCredentials() }
        findViewById<View>(R.id.btn_registration).setOnClickListener({ v -> navigateToRegistration() })

        presenter = LoginPresenter(this, LoginInteractor())

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    }

    override fun onResume() {
        super.onResume()
        if (mSettings!!.contains(APP_PREFERENCES_ACCESS_TOKEN)) {
            navigateToMain()
        }
    }

    override fun saveAccessToken(accessToken: String) {
        //if (accessToken != null) {
            val editor = mSettings!!.edit()
            editor.putString(APP_PREFERENCES_ACCESS_TOKEN, accessToken)
            editor.apply()
       //}
    }

    override fun onDestroy() {
        presenter!!.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar!!.visibility = View.GONE
    }

    override fun setUsernameError() {
        username!!.error = getString(R.string.username_error)
    }

    override fun setPasswordError() {
        password!!.error = getString(R.string.password_error)
    }

    override fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun navigateToRegistration() {
        startActivity(Intent(this, RegistrationActivity::class.java))
        finish()
    }

    private fun validateCredentials() {
        presenter!!.validateCredentials(username!!.text.toString(), password!!.text.toString())
    }

    override fun showToast(key: String) {
        val strId = resources.getIdentifier(key, "string", packageName)
        Toast.makeText(this, getString(strId), Toast.LENGTH_LONG).show()
    }

    companion object {
        val APP_PREFERENCES = "mainSettings"
        val APP_PREFERENCES_ACCESS_TOKEN = "AccessToken"
    }

}