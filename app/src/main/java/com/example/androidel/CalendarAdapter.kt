package com.example.androidel

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(val dayList: ArrayList<String>): RecyclerView.Adapter<CalendarAdapter.ViewHolder>(){
    private val totalMax = 1
    private var total = 0
    private lateinit var selectedText: TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 년 월 텍스트 변경
        holder.dayText.text = dayList[position]

        // 요일 클릭 이벤트
        holder.itemView.setOnClickListener {
            if (holder.dayText.text != "") {
                if (total < totalMax) {
                    selectedText = holder.dayText
                } else {
                    selectedText.background = null
                    selectedText.setTextColor(Color.parseColor("#040415"))
                    selectedText = holder.dayText
                    total -= 1
                }
                holder.dayText.setBackgroundResource(R.drawable.record_round_button)
                holder.dayText.setTextColor(Color.WHITE)
                total += 1
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