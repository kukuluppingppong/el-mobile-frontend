package com.example.androidel

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidel.databinding.ActivityTrainerBinding

class TrainerActivity : AppCompatActivity() {
    val binding by lazy { ActivityTrainerBinding.inflate(layoutInflater) }
    private lateinit var trainerAdapter: TrainerAdapter
    private lateinit var choiceDay: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        choiceDay = ChoiceDay.choiceDaySave

        var trainerList = arrayListOf(
            TrainerDataClass( R.drawable.sample, "홍길동", "월요일", "14:00~15:00", false),
            TrainerDataClass( R.drawable.sample, "홍동길", "화요일", "14:00~15:00", false),
            TrainerDataClass( R.drawable.sample, "123", "수요일", "14:00~15:00", false),
            TrainerDataClass( R.drawable.sample, "135324", "목요일", "14:00~15:00", false),
            TrainerDataClass( R.drawable.sample, "13242", "금요일", "14:00~15:00", false),
            TrainerDataClass( R.drawable.sample, "124321124", "토요일", "14:00~15:00", false))

        var trainerListCopy = ArrayList<TrainerDataClass>()
        for( a in 0 until trainerList.size) {
            for( b in 0 until choiceDay!!.size )
             if(trainerList[a].day.contains(choiceDay[b])) {
                 trainerListCopy.add(trainerList[a])
             }
        }

        trainerAdapter = TrainerAdapter(applicationContext, trainerListCopy)
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