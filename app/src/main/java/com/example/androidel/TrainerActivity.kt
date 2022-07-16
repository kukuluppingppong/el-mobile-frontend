package com.example.androidel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidel.databinding.ActivityTrainerBinding

class TrainerActivity : AppCompatActivity() {
    val binding by lazy { ActivityTrainerBinding.inflate(layoutInflater) }
    private lateinit var trainerAdapter:TrainerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var trainerList = mutableListOf(
            TrainerDataClass(1, R.drawable.sample, "홍길동", "월요일", "14:00~15:00", false),
            TrainerDataClass(2, R.drawable.sample, "홍동길", "월요일", "14:00~15:00", false),
            TrainerDataClass(3, R.drawable.sample, "123", "월요일", "14:00~15:00", false),
            TrainerDataClass(4, R.drawable.sample, "135324", "월요일", "14:00~15:00", false),
            TrainerDataClass(5, R.drawable.sample, "13242", "월요일", "14:00~15:00", false),
            TrainerDataClass(6, R.drawable.sample, "124321124", "월요일", "14:00~15:00", false))

        trainerAdapter = TrainerAdapter(applicationContext, trainerList)
        binding.recyclerView.adapter = trainerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.btnNext.setOnClickListener {
            if (trainerAdapter.choiceTrainer.isNotEmpty()) {
                startActivity(trainerAdapter.intent)
                overridePendingTransition(0, 0)
            }
            else {
                Toast.makeText(applicationContext, "선택된 트레이너가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            trainerAdapter.choiceTrainer.clear()
            var intent = Intent(applicationContext, DaychoiceActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}