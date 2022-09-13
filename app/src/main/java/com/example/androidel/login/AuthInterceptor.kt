package com.example.androidel.login

import com.example.androidel.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request().newBuilder()
            .addHeader("Authorization", MyApplication.prefs.token ?: "").build()
        return chain.proceed(req)
    }
}