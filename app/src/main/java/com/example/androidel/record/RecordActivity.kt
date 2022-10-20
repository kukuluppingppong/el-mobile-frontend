package com.example.androidel.record

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.androidel.MyApplication
import com.example.androidel.databinding.ActivityRecordBinding
import com.example.androidel.record.models.RecordDietResult
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

    private lateinit var foodLayoutList: ArrayList<LinearLayout>
    private lateinit var foodImageList: ArrayList<ImageView>
    private lateinit var timeList: ArrayList<TextView>
    private lateinit var amountList: ArrayList<TextView>
    private lateinit var scoreList: ArrayList<RatingBar>

    private lateinit var videoList: ArrayList<VideoView>

    private lateinit var mediaController : MediaController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.scroll.smoothScrollTo(0, 0)

        setList = arrayListOf(binding.sets1, binding.sets2, binding.sets3)
        repList = arrayListOf(binding.reps1, binding.reps2, binding.reps3)
        degreeList = arrayListOf(binding.degree1, binding.degree2, binding.degree3)
        partList = arrayListOf(binding.parts1, binding.parts2, binding.parts3)

        foodLayoutList = arrayListOf(binding.foodLayout1, binding.foodLayout2, binding.foodLayout3)
        foodImageList = arrayListOf(binding.food1, binding.food2, binding.food3)
        timeList = arrayListOf(binding.time1, binding.time2, binding.time3)
        amountList = arrayListOf(binding.amount1, binding.amount2, binding.amount3)
        scoreList = arrayListOf(binding.score1, binding.score2, binding.score3)

        videoList = arrayListOf(binding.video1, binding.video2, binding.video3)

        binding.btnExercise.isSelected = true

        selectedDate = LocalDate.now()

        setMonthView()
        callWorkoutData()

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

            for(i in 0..2) {
                foodLayoutList[i].visibility = View.GONE
            }
            callWorkoutData()
        }

        binding.btnFood.setOnClickListener {
            binding.btnExercise.isSelected = false
            binding.btnFood.isSelected = true
            binding.foodLayout.visibility = View.VISIBLE
            binding.exerciseLayout.visibility = View.GONE
            callDietData()
        }

        mediaController = MediaController(RecordActivity@this)

        binding.video1.setOnPreparedListener {
            binding.video1.seekTo(1)
            binding.video1.pause()
        }

        binding.video2.setOnPreparedListener {
            binding.video2.seekTo(1)
            binding.video2.pause()
        }

        binding.video3.setOnPreparedListener {
            binding.video3.seekTo(1)
            binding.video3.pause()
        }

        binding.btnWork1.setOnClickListener {
            videoList[0].setMediaController(mediaController)
            binding.video1.start()
            binding.video2.pause()
            binding.video3.pause()
        }

        binding.btnWork2.setOnClickListener {
            videoList[1].setMediaController(mediaController)
            binding.video2.start()
            binding.video1.pause()
            binding.video3.pause()
        }

        binding.btnWork3.setOnClickListener {
            videoList[2].setMediaController(mediaController)
            binding.video3.start()
            binding.video1.pause()
            binding.video2.pause()
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
                    callWorkoutData()
                }
                if (binding.foodLayout.visibility == View.VISIBLE) {
                    callDietData()
                }
            }
        )
        binding.recyclerView.adapter = CalendarAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 7)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callWorkoutData() {
        val call: Call<RecordWorkoutResult> = MyApplication.recordService.getWorkoutRecordList(
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
                        videoList[i].setVideoPath("https://el-trainer.s3.ap-northeast-2.amazonaws.com/${result[i].video}")
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callDietData() {
        val call: Call<RecordDietResult> = MyApplication.recordService.getDietRecordList(
            MyApplication.prefs.trainerId!!.toInt(), CalendarAdapter.selectedText.replace("-", ""))

        Log.e("태그", CalendarAdapter.selectedText.replace("-", ""))

        call.enqueue(object: Callback<RecordDietResult> {
            override fun onResponse(call: Call<RecordDietResult>, response: Response<RecordDietResult>) {
                if (response.isSuccessful) {
                    val result = response.body()!!.data
                    Log.e("요일별 식단 데이터", response.body().toString())
//                    Log.e("식단 response", response.toString())

                    for(i in 0..2) {
                        foodLayoutList[i].visibility = View.GONE
                    }
                    var scoreTotal = 0

                    for(i in result.indices) {
                        when (result[i].title) {
                            "아침" -> {
                                foodLayoutList[0].visibility = View.VISIBLE
                                timeList[0].text = result[i].time.slice(
                                    result[i].time.indexOf(" ")+1 until result[i].time.lastIndexOf(":"))
                                amountList[0].text = result[i].amount
                                scoreList[0].rating = result[i].score.toFloat()
                                foodGlide(result[i].path, foodImageList[0])
                                scoreTotal += result[i].score
                            }
                            "점심" -> {
                                foodLayoutList[1].visibility = View.VISIBLE
                                timeList[1].text = result[i].time.slice(
                                    result[i].time.indexOf(" ")+1 until result[i].time.lastIndexOf(":"))
                                amountList[1].text = result[i].amount
                                scoreList[1].rating = result[i].score.toFloat()
                                foodGlide(result[i].path, foodImageList[1])
                                scoreTotal += result[i].score

                            }
                            "저녁" -> {
                                foodLayoutList[2].visibility = View.VISIBLE
                                timeList[2].text = result[i].time.slice(
                                    result[i].time.indexOf(" ")+1 until result[i].time.lastIndexOf(":"))
                                amountList[2].text = result[i].amount
                                scoreList[2].rating = result[i].score.toFloat()
                                foodGlide(result[i].path, foodImageList[2])
                                scoreTotal += result[i].score
                            }
                        }
                    }
                    binding.foodTotal.text = result.size.toString()
                    binding.scoreTotal.text = (scoreTotal / result.size).toString()
                } else {
                    Toast.makeText(applicationContext, "저장된 식단 데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RecordDietResult>, t: Throwable) {
                Log.e("요일별 운동 오류", t.toString())
            }
        })
    }

    private fun foodGlide(path: String, position: ImageView) {
        Glide.with(applicationContext)
            .asBitmap()
            .load("https://el-trainer.s3.ap-northeast-2.amazonaws.com/${path}")
            .into(position)
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