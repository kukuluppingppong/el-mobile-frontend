package com.example.androidel

import com.example.androidel.model.TrainerListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("api/training")
    fun getTrainerList(): Call<TrainerListModel>
}