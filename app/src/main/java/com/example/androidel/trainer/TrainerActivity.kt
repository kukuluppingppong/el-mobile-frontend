package com.example.androidel.trainer

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidel.MyApplication
import com.example.androidel.databinding.ActivityTrainerBinding
import com.example.androidel.trainer.model.TrainerJoinResponse
import com.example.androidel.trainer.model.TrainerListModel
import org.json.JSONObject
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
                    var result = response.body()
                    Log.e("태그", result.toString())
                    if (result == null) {
                        var jsonObject = JSONObject(response.errorBody()!!.string())
                        val message = jsonObject.getString("message")
                        Toast.makeText(this@TrainerActivity, message, Toast.LENGTH_SHORT).show()
                    }
                    if (response.isSuccessful) {
                        Toast.makeText(this@TrainerActivity, "매칭 성공", Toast.LENGTH_SHORT).show()
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