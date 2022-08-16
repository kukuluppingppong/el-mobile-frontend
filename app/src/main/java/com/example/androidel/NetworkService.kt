package com.example.androidel

import com.example.androidel.model.TrainerListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("/user")
    fun getTrainerList(
        @Query("success") success: Boolean,
        @Query("statusCode") statusCode: String?,
        @Query("message") message: String?
    ): Call<TrainerListModel>
}
