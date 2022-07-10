package com.example.androidel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrainerAdapter(val trainerList: ArrayList<TrainerDataClass>): RecyclerView.Adapter<TrainerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trainer_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(trainerList[position].image)
        holder.name.text = trainerList[position].name
        holder.day.text = trainerList[position].day
        holder.time.text = trainerList[position].time

        holder.chkBox.setOnClickListener {
            if(holder.chkBox.isChecked) {

            } else {

            }
        }
    }

    override fun getItemCount(): Int {
        return trainerList.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val name = itemView.findViewById<TextView>(R.id.name)
        val day = itemView.findViewById<TextView>(R.id.day)
        val time = itemView.findViewById<TextView>(R.id.time)
        val chkBox = itemView.findViewById<CheckBox>(R.id.chkBox)
    }
}