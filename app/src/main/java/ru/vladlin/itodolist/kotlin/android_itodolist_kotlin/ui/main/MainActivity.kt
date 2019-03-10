package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.R
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.adapters.TasksAdapter
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models.*
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.ui.login.LoginActivity

abstract class MainActivity : AppCompatActivity(), MainView {

    internal abstract var recyclerView: RecyclerView
    internal abstract var adapter: RecyclerView.Adapter<*>

    private var progressBar: ProgressBar? = null
    private var presenter: MainPresenter? = null

//    val accessToken: String
//        get() {
//            val mSettings = getSharedPreferences("mainSettings", Context.MODE_PRIVATE)
//            return mSettings.getString("AccessToken", "")
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progress)
        presenter = MainPresenter(this)

        findViewById<View>(R.id.fab).setOnClickListener { v -> addTask() }

    }

    override fun onResume() {
        super.onResume()
        presenter!!.onResume()
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {

                presenter!!.logout()

                val mSettings = getSharedPreferences("mainSettings", Context.MODE_PRIVATE)
                mSettings.edit().clear().apply()

                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return super.onOptionsItemSelected(item)
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        presenter!!.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        progressBar!!.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        progressBar!!.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    override fun displayTasks(tasksResponse: TasksModel) {
        if (tasksResponse != null) {
            //adapter = TasksAdapter(tasksResponse!!.getData(), this@MainActivity, ???({ v, itemTask -> this.onItemClicked(v, itemTask) }))
            //adapter = new TasksAdapter(tasksResponse.getData(), ru.vladlin.itodolist.ui.main.MainActivity.this, this::onItemClicked);
            adapter = TasksAdapter(tasksResponse.getData(), this@MainActivity, this::onItemClicked)
            recyclerView.adapter = adapter
        } else {
            showToast(getString(R.string.data_response_null))
        }
    }

    internal fun onItemClicked(v: View, itemTask: TaskModel) {
//        val popup = PopupMenu(v.context, v)
//        popup.inflate(R.menu.popupmenu)
//        popup.setOnMenuItemClickListener { item ->
//            when (item.itemId) {
//                R.id.action_edit -> {
//                    updateTask(itemTask)
//                    return@popup.setOnMenuItemClickListener true
//                }
//                R.id.action_delete -> {
//                    deleteTask(itemTask.getId())
//                    return@popup.setOnMenuItemClickListener true
//                }
//                else -> return@popup.setOnMenuItemClickListener
//                false
//            }
//        }
//        popup.show()
    }

    internal fun deleteTask(taskId: String) {
        presenter!!.deleteTask(taskId)
    }

    internal fun addTask() {
//        startActivity(Intent(this, TaskActivity::class.java))
//        finish()
    }

    internal fun updateTask(itemTask: TaskModel) {
//        val intent = Intent(this, TaskActivity::class.java)
//        intent.putExtra("taskId", itemTask.getId())
//        intent.putExtra("taskTitle", itemTask.getTitle())
//        intent.putExtra("taskContent", itemTask.getContent())
//        startActivity(intent)
//        finish()
    }

    override fun showToast(key: String) {
        val strId = resources.getIdentifier(key, "string", packageName)
        Toast.makeText(this, getString(strId), Toast.LENGTH_LONG).show()
    }

}