package com.example.androidel.record

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidel.databinding.ActivityRecordBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class RecordActivity : AppCompatActivity() {
    val binding by lazy { ActivityRecordBinding.inflate(layoutInflater) }

    private lateinit var selectedDate: LocalDate
    private lateinit var CalendarAdapter: CalendarAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnExercise.isSelected = true

        selectedDate = LocalDate.now()

        setMonthView()

        binding.btnPrev.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }

        binding.btnNext.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
        }

        binding.btnExercise.setOnClickListener {
            binding.btnExercise.isSelected = true
            binding.btnFood.isSelected = false
            binding.foodLayout.visibility = View.INVISIBLE
            binding.exerciseLayout.visibility = View.VISIBLE
        }

        binding.btnFood.setOnClickListener {
            binding.btnExercise.isSelected = false
            binding.btnFood.isSelected = true
            binding.foodLayout.visibility = View.VISIBLE
            binding.exerciseLayout.visibility = View.INVISIBLE
        }

        binding.exercise1.setOnClickListener {
            val dlg = RecordDialogActivity(this)
            dlg.show()
        }
    }

    // 형식 변환
    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearFromDate(date: LocalDate): String {
        var formatter = DateTimeFormatter.ofPattern("yyyy년 M월")
        return date.format(formatter)
    }

    // 날짜 적용(화면 갱신)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView() {
        binding.todayDate.text = monthYearFromDate(selectedDate)

        var dayList = daysInMonthArray(selectedDate)

        CalendarAdapter = CalendarAdapter(YearMonth.from(selectedDate), dayList)
        binding.recyclerView.adapter = CalendarAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 7)
    }

    // 요일 설정
    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        var dayList = ArrayList<String>()

        var yearMonth = YearMonth.from(date)
        var lastDay = yearMonth.lengthOfMonth()

        var firstDay = date.withDayOfMonth(1)
        var dayOfWeek = firstDay.dayOfWeek.value

        for(i in 1..41) {
            if (dayOfWeek == 7) {
                dayOfWeek = 0
            }
            if (i <= dayOfWeek || i > lastDay + dayOfWeek) {
                dayList.add("")
            } else {
                dayList.add((i-dayOfWeek).toString())
            }
        }
        return dayList
    }
}