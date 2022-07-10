package com.example.androidel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidel.databinding.ActivityJoinBinding
import com.example.androidel.databinding.ActivityLoginBinding

class JoinActivity : AppCompatActivity() {
    val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnJoin.setOnClickListener {
            val intent = Intent(applicationContext, BasicActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}