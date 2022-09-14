package com.example.androidel.trainer.interfaces

import com.example.androidel.trainer.model.TrainerListModel
import retrofit2.Call
import retrofit2.http.GET

interface TrainerService {
    @GET("api/training/trainers")
    fun getTrainerList(): Call<TrainerListModel>
}