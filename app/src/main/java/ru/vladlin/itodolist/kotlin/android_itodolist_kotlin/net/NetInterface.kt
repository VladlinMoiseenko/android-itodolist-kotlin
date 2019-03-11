package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.net

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models.*

interface NetInterface {

    @POST("v1/register")
    abstract fun register(@Body credentials: Credentials): Observable<ProfileModel>

    @POST("v1/authorize")
    abstract fun authorize(@Body credentials: Credentials): Observable<AuthorizeModel>

    @POST("v1/accesstoken")
    abstract fun accesstoken(@Body token: Token): Observable<AccesstokenModel>

    @GET("v1/logout")
    abstract fun logout(@Header("Authorization") accessToken: String): Observable<LogoutModel>

    @GET("v1/task")
    abstract fun getTasks(@Header("Authorization") accessToken: String): Observable<TasksModel>

    @GET("v1/task/view/{id}")
    abstract fun viewTask(@Path("id") taskId: String, @Header("Authorization") accessToken: String): Observable<Task>

    @DELETE("v1/task/delete/{id}")
    abstract fun deleteTask(@Path("id") taskId: String, @Header("Authorization") accessToken: String): Observable<Task>

    @POST("v1/task/create")
    abstract fun createTask(@Body taskModel: TaskModel, @Header("Authorization") accessToken: String): Observable<Task>

    @PUT("v1/task/update/{id}")
    abstract fun updateTask(@Path("id") taskId: String, @Body taskModel: TaskModel, @Header("Authorization") accessToken: String): Observable<Task>

}