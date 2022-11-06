package com.example.androidel.record.interfaces

import com.example.androidel.record.models.FeedBackResult
import com.example.androidel.record.models.RecordDietResult
import com.example.androidel.record.models.RecordWorkoutResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecordService {
    @GET("api/history/workout/{trainerId}/{date}")
    fun getWorkoutRecordList(
        @Path("trainerId") trainerId: Int,
        @Path("date") date: String
    ): Call<RecordWorkoutResult>

    @GET("api/history/diet/{trainerId}/{date}")
    fun getDietRecordList(
        @Path("trainerId") trainerId: Int,
        @Path("date") date: String
    ): Call<RecordDietResult>

    @GET("api/history/feedback")
    fun getFeedBackList(): Call<FeedBackResult>
}