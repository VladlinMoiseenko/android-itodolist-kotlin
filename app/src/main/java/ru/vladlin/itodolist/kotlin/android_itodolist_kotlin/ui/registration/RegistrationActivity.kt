package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.R
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.main.MainActivity

class RegistrationActivity : AppCompatActivity(), RegistrationView {

    private var progressBar: ProgressBar? = null
    private var username: EditText? = null
    private var password: EditText? = null
    private var email: EditText? = null
    private var presenter: RegistrationPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        progressBar = findViewById(R.id.progress)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        email = findViewById(R.id.email)

        findViewById<View>(R.id.btn_register).setOnClickListener { v -> validateCredentials() }

        presenter = RegistrationPresenter(this, RegistrationInteractor())

    }

    override fun setUsernameError() {
        username!!.error = getString(R.string.username_error)
    }

    override fun setPasswordError() {
        password!!.error = getString(R.string.password_error)
    }

    override fun setEmailError() {
        password!!.error = getString(R.string.email_error)
    }

    private fun validateCredentials() {
        presenter!!.validateCredentials(username!!.text.toString(), password!!.text.toString(), email!!.text.toString())
    }

    override fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun showProgress() {
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar!!.visibility = View.GONE
    }

    override fun showToast(key: String) {
        val strId = resources.getIdentifier(key, "string", packageName)
        Toast.makeText(this, getString(strId), Toast.LENGTH_LONG).show()
    }

    override fun saveAccessToken(accessToken: String) {
        //if (accessToken != null) {
            val mSettings = getSharedPreferences("mainSettings", Context.MODE_PRIVATE)
            val editor = mSettings.edit()
            editor.putString("AccessToken", accessToken)
            editor.apply()
        //}
    }

    override fun onDestroy() {
        presenter!!.onDestroy()
        super.onDestroy()
    }

}