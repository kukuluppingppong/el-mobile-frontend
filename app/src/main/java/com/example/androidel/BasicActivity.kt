package com.example.androidel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidel.databinding.ActivityBasicBinding
import com.example.androidel.databinding.ActivityJoinBinding

class BasicActivity : AppCompatActivity() {
    val binding by lazy { ActivityBasicBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val intent = Intent(applicationContext, DaychoiceActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.rGroup.setOnCheckedChangeListener { group, checkedId ->
            when (binding.rGroup.checkedRadioButtonId) {
                R.id.btnMan -> binding.character.setImageResource(R.drawable.man_character)
                R.id.btnWoman -> binding.character.setImageResource(R.drawable.woman_character)
            }
        }
    }
}