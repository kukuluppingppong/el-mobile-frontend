package com.example.androidel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidel.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.joinButton.setOnClickListener {
            val intent = Intent(applicationContext, JoinActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.btnGoogle.setOnClickListener {
            var intent = Intent(applicationContext, SendActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}