package com.example.androidel

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import com.example.androidel.databinding.ActivityDaychoiceBinding
import java.sql.Time
import java.util.*

class DaychoiceActivity : AppCompatActivity() {
    val binding by lazy { ActivityDaychoiceBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            btnMon.setOnClickListener {
                if (!btnMon.isSelected) {
                    btnMon.isSelected = true
                    btnMon.setTextColor(Color.parseColor("#FFFFFF"))
                } else {
                    btnMon.isSelected = false
                    btnMon.setTextColor(Color.parseColor("#000000"))
                }
            }
        }

        with(binding) {
            btnTue.setOnClickListener {
                if (!btnTue.isSelected) {
                    btnTue.isSelected = true
                    btnTue.setTextColor(Color.parseColor("#FFFFFF"))
                } else {
                    btnTue.isSelected = false
                    btnTue.setTextColor(Color.parseColor("#000000"))
                }
            }
        }

        with(binding) {
            btnWed.setOnClickListener {
                if (!btnWed.isSelected) {
                    btnWed.isSelected = true
                    btnWed.setTextColor(Color.parseColor("#FFFFFF"))
                } else {
                    btnWed.isSelected = false
                    btnWed.setTextColor(Color.parseColor("#000000"))
                }
            }
        }

        with(binding) {
            btnThu.setOnClickListener {
                if (!btnThu.isSelected) {
                    btnThu.isSelected = true
                    btnThu.setTextColor(Color.parseColor("#FFFFFF"))
                } else {
                    btnThu.isSelected = false
                    btnThu.setTextColor(Color.parseColor("#000000"))
                }
            }
        }

        with(binding) {
            btnFri.setOnClickListener {
                if (!btnFri.isSelected) {
                    btnFri.isSelected = true
                    btnFri.setTextColor(Color.parseColor("#FFFFFF"))
                } else {
                    btnFri.isSelected = false
                    btnFri.setTextColor(Color.parseColor("#000000"))
                }
            }
        }

        with(binding) {
            btnSat.setOnClickListener {
                if (!btnSat.isSelected) {
                    btnSat.isSelected = true
                    btnSat.setTextColor(Color.parseColor("#FFFFFF"))
                } else {
                    btnSat.isSelected = false
                    btnSat.setTextColor(Color.parseColor("#000000"))
                }
            }
        }

        with(binding) {
            btnSun.setOnClickListener {
                if (!btnSun.isSelected) {
                    btnSun.isSelected = true
                    btnSun.setTextColor(Color.parseColor("#FFFFFF"))
                } else {
                    btnSun.isSelected = false
                    btnSun.setTextColor(Color.parseColor("#000000"))
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
            val intent = Intent(applicationContext, TrainerActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}