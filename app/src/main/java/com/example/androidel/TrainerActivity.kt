package com.example.androidel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidel.databinding.ActivityTrainerBinding

class TrainerActivity : AppCompatActivity() {
    val binding by lazy { ActivityTrainerBinding.inflate(layoutInflater) }
    private lateinit var trainerAdapter:TrainerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var trainerList = arrayListOf(
            TrainerDataClass(R.drawable.sample, "홍길동", "월요일", "14:00~15:00"),
            TrainerDataClass(R.drawable.sample, "홍동길", "월요일", "14:00~15:00"),
            TrainerDataClass(R.drawable.sample, "123", "월요일", "14:00~15:00"),
            TrainerDataClass(R.drawable.sample, "135324", "월요일", "14:00~15:00"),
            TrainerDataClass(R.drawable.sample, "13242", "월요일", "14:00~15:00"),
            TrainerDataClass(R.drawable.sample, "124321124", "월요일", "14:00~15:00"))


        trainerAdapter = TrainerAdapter(trainerList)
        binding.recyclerView.adapter = trainerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}