package com.example.androidel

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidel.databinding.ActivityBasicBinding

class BasicActivity : AppCompatActivity() {
    val binding by lazy { ActivityBasicBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var rdoCheck = false

        binding.btnNext.setOnClickListener {
//            if(binding.edtHeight.length() != 3) {
//                Toast.makeText(applicationContext, "다시 입력해주세요.", Toast.LENGTH_SHORT).show()
//                binding.edtHeight.requestFocus()
//                return@setOnClickListener
//            }
//
//            if(binding.edtWeight.length() < 2) {
//                Toast.makeText(applicationContext, "다시 입력해주세요.", Toast.LENGTH_SHORT).show()
//                binding.edtWeight.requestFocus()
//                return@setOnClickListener
//            }
//
//            if (!rdoCheck) {
//                Toast.makeText(applicationContext, "성별을 체크해주세요.", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

            val intent = Intent(applicationContext, DaychoiceActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

//        binding.rGroup.setOnCheckedChangeListener { group, checkedId ->
//            when (binding.rGroup.checkedRadioButtonId) {
//                R.id.btnMan -> binding.character.setImageResource(R.drawable.man_character)
//                R.id.btnWoman -> binding.character.setImageResource(R.drawable.woman_character)
//            }
//            rdoCheck = true
//        }
    }
}