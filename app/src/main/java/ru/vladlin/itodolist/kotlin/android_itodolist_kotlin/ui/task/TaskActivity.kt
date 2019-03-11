package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.task

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

class TaskActivity : AppCompatActivity(), TaskView {

    private var progressBar: ProgressBar? = null
    private var task_title: EditText? = null
    private var task_content: EditText? = null
    private var presenter: TaskPresenter? = null
    private var taskId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        task_title = findViewById(R.id.task_title)
        task_content = findViewById(R.id.task_content)

        val intent = intent
        taskId = intent.getStringExtra("taskId")
        task_title!!.setText(intent.getStringExtra("taskTitle"))
        task_content!!.setText(intent.getStringExtra("taskContent"))

        findViewById<View>(R.id.btn_save).setOnClickListener { v -> validateTask() }

        progressBar = findViewById(R.id.progress)
        presenter = TaskPresenter(this, TaskInteractor())
    }

    override fun showProgress() {
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar!!.visibility = View.GONE
    }

    override fun setTitleError() {
        task_title!!.error = getString(R.string.title_error)
    }

    private fun validateTask() {
        presenter!!.validateTask(task_title!!.text.toString(), task_content!!.text.toString())
    }

    override fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun getAccessToken(): String {
        val mSettings = getSharedPreferences("mainSettings", Context.MODE_PRIVATE)
        return mSettings.getString("AccessToken", "")
    }

    override fun getIdTask(): String {
        return this.taskId
    }

    override fun onDestroy() {
        presenter!!.onDestroy()
        super.onDestroy()
    }

    override fun showToast(key: String) {
        val strId = resources.getIdentifier(key, "string", packageName)
        Toast.makeText(this, getString(strId), Toast.LENGTH_LONG).show()
    }

}