package com.example.androidel.trainer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidel.MyApplication
import com.example.androidel.WelcomeActivity
import com.example.androidel.databinding.ActivityTrainerBinding
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
                Log.e("태그", response.body()?.trainer.toString())
                Log.e("태그", response.toString())
                if (response.isSuccessful) {

                    trainerAdapter = TrainerAdapter(response.body()?.trainer)
                    binding.recyclerView.adapter = trainerAdapter
                    binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                }
            }

            override fun onFailure(call: Call<TrainerListModel>, t: Throwable) {
                Log.e("태그", t.toString())
            }
        })


//        var trainerList = arrayListOf(
//            TrainerItemModel("홍길동", "남자", "1년"),
//            TrainerItemModel( "홍동길", "남자", "2년"),
//            TrainerItemModel( "홍길동", "남자", "1년"),
//            TrainerItemModel( "홍길동", "남자", "4년"),
//            TrainerItemModel( "홍길동아", "남자", "5년"),
//            TrainerItemModel( "홍길동후", "남자", "1년"))
//
//
//        trainerAdapter = TrainerAdapter(trainerList)
//        binding.recyclerView.adapter = trainerAdapter
//        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        binding.btnNext.setOnClickListener {
            var intent = Intent(applicationContext, WelcomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}