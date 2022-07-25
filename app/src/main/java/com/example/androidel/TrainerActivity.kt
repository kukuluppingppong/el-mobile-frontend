package com.example.androidel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidel.databinding.ActivityTrainerBinding

class TrainerActivity : AppCompatActivity() {
    val binding by lazy { ActivityTrainerBinding.inflate(layoutInflater) }
    private lateinit var trainerAdapter: TrainerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var trainerList = arrayListOf(
            TrainerDataClass( R.drawable.sample, "홍길동", "월요일", "14:00~15:00", false),
            TrainerDataClass( R.drawable.sample, "홍동길", "화요일", "14:00~15:00", false),
            TrainerDataClass( R.drawable.sample, "123", "수요일", "14:00~15:00", false),
            TrainerDataClass( R.drawable.sample, "135324", "목요일", "14:00~15:00", false),
            TrainerDataClass( R.drawable.sample, "13242", "금요일", "14:00~15:00", false),
            TrainerDataClass( R.drawable.sample, "124321124", "토요일", "14:00~15:00", false))


        trainerAdapter = TrainerAdapter(trainerList)
        binding.recyclerView.adapter = trainerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.btnNext.setOnClickListener {
            var intent = Intent(applicationContext, WelcomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}