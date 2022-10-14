package com.example.androidel.trainer.interfaces

import com.example.androidel.login.models.TrainerResponse
import com.example.androidel.trainer.model.TrainerJoinResponse
import com.example.androidel.trainer.model.TrainerListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TrainerService {
    @GET("api/training/trainers")
    fun getTrainerList(): Call<TrainerListModel>

    @POST("api/training/{trainerId}/join")
    fun getTrainerJoin(
        @Path("trainerId") trainerId: Int
    ): Call<TrainerJoinResponse>

    @GET("api/training")
    fun trainerGet(): Call<TrainerResponse>
}