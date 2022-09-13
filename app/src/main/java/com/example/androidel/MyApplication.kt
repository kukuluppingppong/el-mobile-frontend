package com.example.androidel

import android.app.Application
import com.example.androidel.login.AuthInterceptor
import com.example.androidel.login.Prefs
import com.example.androidel.login.interfaces.LoginService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {
    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }

    companion object {
        lateinit var prefs: Prefs

        val BASE_URL = "http://ec2-13-125-255-47.ap-northeast-2.compute.amazonaws.com:8080/"
        val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()

        var networkService: LoginService

        val retrofit: Retrofit
            get() = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        init {
            networkService = retrofit.create(LoginService::class.java)
        }
    }
}