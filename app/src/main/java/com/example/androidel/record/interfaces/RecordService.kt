package com.example.androidel.record.interfaces

import com.example.androidel.record.models.RecordWorkoutResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecordService {
    @GET("api/history/workout/{trainerId}/{date}")
    fun getRecordList(
        @Path("trainerId") trainerId: Int,
        @Path("date") date: String
    ): Call<RecordWorkoutResult>
}