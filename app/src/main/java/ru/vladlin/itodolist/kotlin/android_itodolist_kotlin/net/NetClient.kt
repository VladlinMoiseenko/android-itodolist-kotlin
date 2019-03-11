package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetClient {

    private var retrofit: Retrofit? = null

    fun getRetrofit(): Retrofit {

        if (retrofit == null) {
            val builder = OkHttpClient.Builder()
            val okHttpClient = builder.build()

            retrofit = Retrofit.Builder()
                    .baseUrl("http://apitdlist.vladlin.ru/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
        }

        return retrofit as Retrofit
    }
}
