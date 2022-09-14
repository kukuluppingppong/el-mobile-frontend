package com.example.androidel.login

import com.example.androidel.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = "Bearer ${MyApplication.prefs.token}"
        var req = chain.request().newBuilder().addHeader("Authorization", token).build()
        return chain.proceed(req)
    }
}