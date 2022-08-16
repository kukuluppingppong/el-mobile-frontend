package com.example.androidel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidel.databinding.ActivityTrainerBinding
import com.example.androidel.model.TrainerItemModel

class TrainerActivity : AppCompatActivity() {
    val binding by lazy { ActivityTrainerBinding.inflate(layoutInflater) }
    private lateinit var trainerAdapter: TrainerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var trainerList = arrayListOf(
            TrainerItemModel( R.drawable.sample, "홍길동", "남자", "1년"),
            TrainerItemModel( R.drawable.sample, "홍동길", "남자", "2년"),
            TrainerItemModel( R.drawable.sample, "홍길동", "남자", "1년"),
            TrainerItemModel( R.drawable.sample, "홍길동", "남자", "4년"),
            TrainerItemModel( R.drawable.sample, "홍길동아", "남자", "5년"),
            TrainerItemModel( R.drawable.sample, "홍길동후", "남자", "1년"))


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