package com.example.androidel.record

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.androidel.R
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
class CalendarAdapter(val yearMonth: YearMonth, val dayList: ArrayList<String>, val onClick: () -> Unit)
    : RecyclerView.Adapter<CalendarAdapter.ViewHolder>(){
    private val totalMax = 1
    private var total = 0
    private lateinit var selectedTextView : TextView

    private val date = LocalDate.now()
    var selectedText = "${date.year}${date.monthValue}${date.dayOfMonth}"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 년 월 텍스트 변경
        holder.dayText.text = dayList[position]

        if("$yearMonth" == "${date.year}-${date.monthValue}" && holder.dayText.text == "${date.dayOfMonth}") {
            total += 1
            selectedTextView = holder.dayText
            holder.dayText.setBackgroundResource(R.drawable.record_round_button)
            holder.dayText.setTextColor(Color.WHITE)
        }

        // 요일 클릭 이벤트
        holder.itemView.setOnClickListener {
            if (holder.dayText.text != "") {
                if (total < totalMax) {
                    selectedTextView = holder.dayText
                } else {
                    selectedTextView.background = null
                    selectedTextView.setTextColor(Color.parseColor("#040415"))
                    selectedTextView = holder.dayText
                    total -= 1
                }
                holder.dayText.setBackgroundResource(R.drawable.record_round_button)
                holder.dayText.setTextColor(Color.WHITE)
                total += 1
                selectedText = "${date.year}${date.monthValue}${selectedTextView.text}"
                onClick.invoke()
            }
        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val dayText = itemView.findViewById<TextView>(R.id.dayText)
    }
}