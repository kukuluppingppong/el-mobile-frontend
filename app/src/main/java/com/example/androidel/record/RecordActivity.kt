package com.example.androidel.record

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidel.MyApplication
import com.example.androidel.databinding.ActivityRecordBinding
import com.example.androidel.record.models.RecordWorkoutResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class RecordActivity : AppCompatActivity() {
    val binding by lazy { ActivityRecordBinding.inflate(layoutInflater) }

    private lateinit var selectedDate: LocalDate
    private lateinit var CalendarAdapter: CalendarAdapter

    private lateinit var setList: ArrayList<TextView>
    private lateinit var repList: ArrayList<TextView>
    private lateinit var degreeList: ArrayList<TextView>
    private lateinit var partList: ArrayList<TextView>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setList = arrayListOf(binding.sets1, binding.sets2, binding.sets3)
        repList = arrayListOf(binding.reps1, binding.reps2, binding.reps3)
        degreeList = arrayListOf(binding.degree1, binding.degree2, binding.degree3)
        partList = arrayListOf(binding.parts1, binding.parts2, binding.parts3)

        binding.btnExercise.isSelected = true

        selectedDate = LocalDate.now()

        setMonthView()
        callData()

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
            binding.foodLayout.visibility = View.GONE
            binding.exerciseLayout.visibility = View.VISIBLE
        }

        binding.btnFood.setOnClickListener {
            binding.btnExercise.isSelected = false
            binding.btnFood.isSelected = true
            binding.foodLayout.visibility = View.VISIBLE
            binding.exerciseLayout.visibility = View.GONE
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

        CalendarAdapter = CalendarAdapter(YearMonth.from(selectedDate), dayList,
            onClick = {
                if (binding.exerciseLayout.visibility == View.VISIBLE) {
                    callData()
                }
            }
        )
        binding.recyclerView.adapter = CalendarAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 7)

        Log.e("태그", selectedDate.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callData() {
        val call: Call<RecordWorkoutResult> = MyApplication.recordService.getRecordList(
            MyApplication.prefs.trainerId!!.toInt(), CalendarAdapter.selectedText.replace("-", ""))

        Log.e("태그", CalendarAdapter.selectedText.replace("-", ""))

        call.enqueue(object: Callback<RecordWorkoutResult> {
            override fun onResponse(call: Call<RecordWorkoutResult>, response: Response<RecordWorkoutResult>) {
                if (response.isSuccessful) {
                    val result = response.body()!!.data
                    Log.e("요일별 운동 데이터", response.body().toString())
                    binding.time.text = result[0].time

                    for(i in 0..2) {
                        setList[i].text = result[i].sets.toString()
                        repList[i].text = result[i].reps.toString()
                        degreeList[i].text = result[i].degree + ", "
                        partList[i].text = result[i].parts.joinToString()
                    }
                } else {
                    Toast.makeText(applicationContext, "저장된 운동 데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RecordWorkoutResult>, t: Throwable) {
                Log.e("요일별 운동 오류", t.toString())
            }
        })
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