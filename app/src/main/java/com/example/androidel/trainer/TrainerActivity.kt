package com.example.androidel.trainer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidel.MyApplication
import com.example.androidel.WelcomeActivity
import com.example.androidel.databinding.ActivityTrainerBinding
import com.example.androidel.trainer.model.TrainerJoinResponse
import com.example.androidel.trainer.model.TrainerListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrainerActivity : AppCompatActivity() {
    val binding by lazy { ActivityTrainerBinding.inflate(layoutInflater) }
    private lateinit var trainerAdapter: TrainerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val call: Call<TrainerListModel> = MyApplication.trainerService.getTrainerList()

        call?.enqueue(object: Callback<TrainerListModel> {
            override fun onResponse(
                call: Call<TrainerListModel>,
                response: Response<TrainerListModel>,
            ) {
                Log.e("TrainerActivity1", response.body()?.trainer.toString())
                Log.e("TrainerActivity1", response.toString())
                if (response.isSuccessful) {
                    trainerAdapter = TrainerAdapter(response.body()?.trainer)
                    binding.recyclerView.adapter = trainerAdapter
                    binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                }
            }

            override fun onFailure(call: Call<TrainerListModel>, t: Throwable) {
                Log.e("TrainerActivity1", t.toString())
            }
        })

        binding.btnNext.setOnClickListener {
            val call2: Call<TrainerJoinResponse> = MyApplication.trainerService.getTrainerJoin(trainerAdapter.trainerId!!)

            call2.enqueue(object: Callback<TrainerJoinResponse> {
                override fun onResponse(
                    call: Call<TrainerJoinResponse>,
                    response: Response<TrainerJoinResponse>,
                ) {
                    Log.e("TrainerActivity2", response.body().toString())
                    if (response.isSuccessful) {

//                    var intent = Intent(applicationContext, WelcomeActivity::class.java)
//                    startActivity(intent)
//                    overridePendingTransition(0, 0)
                    }
                }

                override fun onFailure(call: Call<TrainerJoinResponse>, t: Throwable) {
                    Log.e("TrainerActivity2", t.toString())
                }
            })
        }
    }
}