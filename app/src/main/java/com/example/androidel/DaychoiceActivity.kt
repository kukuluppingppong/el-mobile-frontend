package com.example.androidel

import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidel.databinding.ActivityDaychoiceBinding

class DaychoiceActivity : AppCompatActivity() {
    val binding by lazy { ActivityDaychoiceBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var choiceDay = ArrayList<String>()
        var choiceDaySize = 0

        with(binding) {
            btnMon.setOnClickListener {
                if (!btnMon.isSelected) {
                    btnMon.isSelected = true
                    btnMon.setTextColor(Color.parseColor("#FFFFFF"))
                    choiceDay.add(btnMon.text.toString())
                    choiceDaySize = choiceDay.size-1
                } else {
                    btnMon.isSelected = false
                    btnMon.setTextColor(Color.parseColor("#000000"))
                    choiceDay.removeAt(choiceDaySize)
                    choiceDaySize = choiceDay.size-1
                }
            }

            btnTue.setOnClickListener {
                if (!btnTue.isSelected) {
                    btnTue.isSelected = true
                    btnTue.setTextColor(Color.parseColor("#FFFFFF"))
                    choiceDay.add(btnTue.text.toString())
                    choiceDaySize = choiceDay.size-1
                } else {
                    btnTue.isSelected = false
                    btnTue.setTextColor(Color.parseColor("#000000"))
                    choiceDay.removeAt(choiceDaySize)
                    choiceDaySize = choiceDay.size-1
                }
            }

            btnWed.setOnClickListener {
                if (!btnWed.isSelected) {
                    btnWed.isSelected = true
                    btnWed.setTextColor(Color.parseColor("#FFFFFF"))
                    choiceDay.add(btnWed.text.toString())
                    choiceDaySize = choiceDay.size-1
                } else {
                    btnWed.isSelected = false
                    btnWed.setTextColor(Color.parseColor("#000000"))
                    choiceDay.removeAt(choiceDaySize)
                    choiceDaySize = choiceDay.size-1
                }
            }

            btnThu.setOnClickListener {
                if (!btnThu.isSelected) {
                    btnThu.isSelected = true
                    btnThu.setTextColor(Color.parseColor("#FFFFFF"))
                    choiceDay.add(btnThu.text.toString())
                    choiceDaySize = choiceDay.size-1
                } else {
                    btnThu.isSelected = false
                    btnThu.setTextColor(Color.parseColor("#000000"))
                    choiceDay.removeAt(choiceDaySize)
                    choiceDaySize = choiceDay.size-1
                }
            }

            btnFri.setOnClickListener {
                if (!btnFri.isSelected) {
                    btnFri.isSelected = true
                    btnFri.setTextColor(Color.parseColor("#FFFFFF"))
                    choiceDay.add(btnFri.text.toString())
                    choiceDaySize = choiceDay.size-1
                } else {
                    btnFri.isSelected = false
                    btnFri.setTextColor(Color.parseColor("#000000"))
                    choiceDay.removeAt(choiceDaySize)
                    choiceDaySize = choiceDay.size-1
                }
            }

            btnSat.setOnClickListener {
                if (!btnSat.isSelected) {
                    btnSat.isSelected = true
                    btnSat.setTextColor(Color.parseColor("#FFFFFF"))
                    choiceDay.add(btnSat.text.toString())
                    choiceDaySize = choiceDay.size-1
                } else {
                    btnSat.isSelected = false
                    btnSat.setTextColor(Color.parseColor("#000000"))
                    choiceDay.removeAt(choiceDaySize)
                    choiceDaySize = choiceDay.size-1
                }
            }

            btnSun.setOnClickListener {
                if (!btnSun.isSelected) {
                    btnSun.isSelected = true
                    btnSun.setTextColor(Color.parseColor("#FFFFFF"))
                    choiceDay.add(btnSun.text.toString())
                    choiceDaySize = choiceDay.size-1
                } else {
                    btnSun.isSelected = false
                    btnSun.setTextColor(Color.parseColor("#000000"))
                    choiceDay.removeAt(choiceDaySize)
                    choiceDaySize = choiceDay.size-1
                }
            }
        }

        binding.btnFirst.setOnClickListener {
            //val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                var hourText = if(hourOfDay in 0..9) {
                    "0${hourOfDay}"
                } else {
                    "$hourOfDay"
                }

                var minuteText = if (minute in 0..9) {
                    "0${minute}"
                } else {
                    "$minute"
                }
                binding.textFirst.text = "${hourText}:${minuteText}"
            }
            TimePickerDialog(this, timeSetListener, 9, 0, true).show()
        }

        binding.btnLast.setOnClickListener {
            //val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                var hourText = if(hourOfDay in 0..9) {
                    "0${hourOfDay}"
                } else {
                    "$hourOfDay"
                }

                var minuteText = if (minute in 0..9) {
                    "0${minute}"
                } else {
                    "$minute"
                }

                binding.textLast.text = "${hourText}:${minuteText}"
            }
            TimePickerDialog(this, timeSetListener, 18, 0, true).show()
        }

        binding.btnNext.setOnClickListener {
//            if(choiceDay.isEmpty()) {
//                Toast.makeText(applicationContext, "선택된 요일이 없습니다.", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

            ChoiceDay.choiceDaySave = choiceDay
            val intent = Intent(applicationContext, TrainerActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}