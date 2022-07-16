package com.example.androidel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidel.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    val binding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textName.text = "${User.name}님"
    }
}